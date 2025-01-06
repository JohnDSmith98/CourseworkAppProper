package com.example.courseworkappproper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

public class API {

    private static final String BASE_URL = "http://10.224.41.11/comp2000";
    private static RequestQueue requestQueue;
    private static final Gson gson = new Gson();

    // Initialize RequestQueue if needed
    private static void initQueue(Context context) {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
    }

    // Method to get all employees - updated to include callback functionality for constant API updates - kept as non lambda
    public static void getAllEmployees(Context context, EmpsRecieved callback) {
        initQueue(context);
        String url = BASE_URL + "/employees";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Type listType = new TypeToken<List<Employee>>() {}.getType();
                        List<Employee> employees = gson.fromJson(response.toString(), listType);
                        callback.Success(employees);

                        // Handle the list of employees
                        for (Employee employee : employees) {
                            System.out.println(employee.getFirstname());

                            // Log the employee info
                            Log.d("EmployeeInfo", "Firstname: " +
                                    employee.getFirstname() + ", Salary: " + employee.getSalary());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", "Error fetching employees: " + error.getMessage());
                        callback.Error(error);
                    }
                }
        );

// Add the request to the RequestQueue
        requestQueue.add(request);

    }

    // Method to get a single employee based on their ID
    public static void getAnEmployee(Context context, int id, EmpRecieved callback) {
        initQueue(context);
        String url = BASE_URL + "/employees/get/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    Employee employee = gson.fromJson(response.toString(), Employee.class);
                    callback.Success(employee);
                },
                error -> {
                        Log.e("Volley", "Error fetching employee: " + error.getMessage());
                        callback.Error(error);
                }
        );
        requestQueue.add(request);
}

    //Method to add an Employee - also kept as non-lambda - updated to fit the constant API updates
    public static void addEmployee(Context context, Employee employee, OnResponse callback) {
        initQueue(context);
        String url = BASE_URL + "/employees/add";

        try {
            JSONObject jsonRequest = new JSONObject(gson.toJson(employee));

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // onSuccess behavior - Log the success message from the API response
                            String message = response.optString("message", "Employee added successfully");
                            Log.d("EmployeeService", message);
                            callback.Success(message);
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // onError behavior - Log the error
                            Log.e("EmployeeError", "Error adding employee: " + error.getMessage());
                            callback.Error(error);
                        }
                    }
            );

            requestQueue.add(request);
        } catch (JSONException e) {
            Log.e("EmployeeError", "Invalid JSON format: " + e.getMessage());
        }
    }


    // Method to delete an employee
    public static void deleteEmployee(Context context, int id, OnResponse callback) {
        initQueue(context);
        String url = BASE_URL + "/employees/delete/" + id;

        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // onSuccess behavior - Log success message
                        Log.d("EmployeeService", "Employee deleted successfully");
                        callback.Success(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // onError behavior - Log the error
                        Log.e("EmployeeError", "Error deleting employee: " + error.getMessage());
                        callback.Error(error);
                    }
                }
        );

        requestQueue.add(request);
    }

    public static void HealthCheck(Context context, HealthCheck callback){
        initQueue(context);
        String url = BASE_URL + "/health";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        String status = response.optString("message", "Health check passed");
                        Log.d("EmployeeService", status);
                        callback.Success(status);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("HealthError", "Error with health check: " + error.getMessage());
                        callback.Error(error);
                    }
                }
        );
                requestQueue.add(request);
    }


    public interface EmpRecieved {
    void Success(Employee employee);
    void Error(VolleyError error);
    }

    public interface EmpsRecieved {
        void Success(List<Employee> employees);
        void Error(VolleyError error);
    }

    public interface HealthCheck{
        void Success(String status);
        void Error(VolleyError error);
    }

    public interface OnResponse{
        void Success(String message);
        void Error(VolleyError error);
    }
}

