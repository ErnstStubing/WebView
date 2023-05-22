package com.example.webview;

import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CRUDActivity extends AppCompatActivity {

    EditText txt_code, txt_name, txt_description;
    Button btn_save, btn_update, btn_delete, btn_search;
    List<String> data;
    ListView listData;

    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crud);

        initUI();
        String text = getIntent().getStringExtra("text");
        textView1.setText(text);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        listData.setAdapter(adapter);
        listWs(adapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_code = txt_code.getText().toString();
                String s_name = txt_name.getText().toString();
                String s_description = txt_description.getText().toString();
                postFromButtonWs(s_code, s_name, s_description);
                cleanForm();
                chargeListView();
            }
        });
    }

    private void initUI() {
        txt_code = (EditText) findViewById(R.id.txt_code);
        txt_name = (EditText) findViewById(R.id.txt_name);
        txt_description = (EditText) findViewById(R.id.txt_description);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_update = (Button) findViewById(R.id.btn_update);
        btn_search = (Button) findViewById(R.id.btn_search);
        btn_delete = (Button) findViewById(R.id.btn_delete);
        listData = (ListView) findViewById(R.id.lv_1);
        textView1 = (TextView) findViewById(R.id.textView1);

    }

    private void chargeListView() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, data);
        listData.setAdapter(adapter);
        listWs(adapter);
    }

    private void cleanForm() {
        txt_code.setText("");
        txt_name.setText("");
        txt_description.setText("");

    }

    private void reedWs() {
        String url = "https://jsonplaceholder.typicode.com/todos/" + txt_code.getText().toString();

        cleanForm();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    txt_code.setText(jsonObject.getString("code"));
                    txt_name.setText(jsonObject.getString("name"));
                    txt_description.setText(jsonObject.getString("description"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        });

        Volley.newRequestQueue(this).add(postRequest);
    }

    private void postFromButtonWs(final String code, final String name, final String description) {
        String url = "http://20.231.202.18:8000/api/form";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(CRUDActivity.this, "Proyecto: " + name + " creado exitosamente", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("code", code);
                params.put("name", name);
                params.put("description", description);

                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void updatedWs(final String code, final String name, final String description) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";
        StringRequest postRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Toast.makeText(CRUDActivity.this, "Id es: " + jsonObject.getString("id"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());
            }
        }) {
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void deleteWs(final String code) {
        String url = "https://jsonplaceholder.typicode.com/posts/1";

        StringRequest postRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {//metodo se ejecuta cuando hay respuesta del web service
                try {
                    JSONObject jsonObject = new JSONObject(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error de consulta el API REST", error.getMessage());

            }
        });
        Volley.newRequestQueue(this).add(postRequest);
    }

    private void listWs(ArrayAdapter adapter) {
        String url = "http://20.231.202.18:8000/api/form";
        data = new ArrayList<String>();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response.length() > 0) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject obj = response.getJSONObject(i);
                            Producto p = new Producto();
                            p.setName(obj.get("name").toString());
                            adapter.add(p.getName());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Do something when error occurred
                        Log.e("error de consulta el API REST", error.getMessage());
                    }
                }
        );
        Volley.newRequestQueue(this).add(jsonArrayRequest);
    }

}