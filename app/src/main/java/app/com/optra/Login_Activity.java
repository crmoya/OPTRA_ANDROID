package app.com.optra;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import app.com.optra.clases.AppSingleton;

public class Login_Activity extends AppCompatActivity {


    Button btnAceptar;
    EditText txtUsuario, txtClave;
    String url = AppSingleton.url + "site/inicio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        btnAceptar = (Button) findViewById(R.id.btnAceptar);

        txtUsuario = (EditText) findViewById(R.id.txtUsuario);
        txtClave = (EditText) findViewById(R.id.txtClave);
        btnAceptar.setOnClickListener(onButtonClick);

    }

    private void setUsuario(int usuarioId){
        SharedPreferences preferencias=getSharedPreferences("datos_optra", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor=preferencias.edit();
        editor.putString("usuario_optra", usuarioId+"");
        editor.commit();
    }

    Button.OnClickListener onButtonClick = new Button.OnClickListener() {



        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            if (v == btnAceptar) {
                final String usuario = txtUsuario.getText().toString();
                final String clave = txtClave.getText().toString();

                try{
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        int usuarioId = jsonObject.getInt("usuario_id");
                                        if(usuarioId > 0){
                                            setUsuario(usuarioId);
                                            Intent intent = new Intent(Login_Activity.this,LectorPDF_Activity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else{
                                            Toast.makeText(getApplicationContext(),"Usuario / Clave no aceptados",Toast.LENGTH_LONG).show();
                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch(Exception ex){
                                        ex.printStackTrace();
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            String errorMessage = error.getMessage();
                        }
                    })
                    {


                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> params = new HashMap<>();

                            params.put("usuario", usuario);
                            params.put("clave", clave);

                            return params;
                        }
                    };
                    AppSingleton.getInstance(Login_Activity.this).addToRequestQue(stringRequest);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }
    };
}
