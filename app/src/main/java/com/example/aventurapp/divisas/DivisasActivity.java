package com.example.aventurapp.divisas;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.aventurapp.consultas.ConsultasActivity;
import com.example.aventurapp.R;
import com.example.aventurapp.gastos.GastosActivity;
import com.example.aventurapp.menu.MainActivity;
import com.google.gson.annotations.SerializedName;

import retrofit2.converter.gson.GsonConverterFactory;

import android.widget.Toast;

import retrofit2.http.GET;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class DivisasActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher {
    private static final String API_BASE_URL = "https://api.currencyapi.com/v3/";
    private static final String API_KEY = "cur_live_XJFrROOsLxAlQVbCFovWqytkDqaiTW60fGfKar1U";
    //Inicialización de variable
    DrawerLayout drawerLayout;
    EditText etx1, etx2;
    TextView txt1, txt2;
    Spinner spn1, spn2;
    ImageButton btn;
    ApiService apiService; //interfaz
    ApiResponse apiResponse; // clase

    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divisas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED); //bloquear rotación
        //Asignación a la variable
        drawerLayout = findViewById(R.id.drawer_layout);

        etx1 = (EditText) findViewById(R.id.edit1);
        etx2 = (EditText) findViewById(R.id.edit2);
        txt1 = (TextView) findViewById(R.id.txt1);
        txt2 = (TextView) findViewById(R.id.txt2);
        spn1 = (Spinner) findViewById(R.id.spinner1);
        spn2 = (Spinner) findViewById(R.id.spinner2);
        etx1.addTextChangedListener(this);
        etx2.addTextChangedListener(this);

        btn = findViewById(R.id.btn);
        btn.setOnClickListener(this);

//        Inicializo ProgressDialog
        progressDialog = new ProgressDialog(DivisasActivity.this);
        progressDialog.setMessage("Cargando datos, por favor espere");
        progressDialog.setCancelable(false);

        //Adaptador de la interfaz Java a llamados HTTP
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //los números que pongo (59) es para un seguimiento de las líneas de códigos para saber
        //en donde tuve el error usando el logcat
        Log.d("DebugGabriel", "(59) Retrofit construido.");

        // Creando interfaz con el adaptador
        apiService = retrofit.create(ApiService.class);

//        Muestro el ProgressDialog antes de la llamada a la API
        progressDialog.show();


        // Invocación de un método Retrofit que envía una petición a un servidor web y envía una respuesta
        // Cada llamada produce su propia respuesta HTTP
        //el getCurrencyData es el dato de la divisa actual
        Call<ApiResponse> call = apiService.getCurrencyData();

        Log.d("DebugGabriel", "(62) Llamada conseguida");

        // Se envía asincronamente la petición y notifica callback de su respuesta
        // o error al crear la petición o procesar la respuesta
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(@NonNull Call<ApiResponse> call, @NonNull Response<ApiResponse> response) {
                try {
                    //Si hay éxito en las respuestas
//                    Y cierro el ProgressDialog al recibir la respuesta
                    progressDialog.dismiss();
                    if (response.isSuccessful()) {
                        Log.d("DebugGabriel", "(77) Respuesta exitosa");

                        // Respuesta igual a contenido
                        apiResponse = response.body();
                        if (apiResponse != null && apiResponse.getData() != null) {

                            // Muestro en log toda la respuesta JSON
                            Log.d("API_RESPONSE", "(84)." + apiResponse.getData().toString());

                            // Lleno los arrays con los códigos "code" del JSON
                            Set<String> codigosDivisa = apiResponse.getData().keySet();
                            ArrayList<String> listaCodigosDivisa = new ArrayList<>(codigosDivisa);
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DivisasActivity.this,
                                    android.R.layout.simple_spinner_item, listaCodigosDivisa);

                            // Lleno los spinner con los arrays
                            spn1.setAdapter(adapter);
                            spn2.setAdapter(adapter);
                            spn1.setSelection(adapter.getPosition("ARS"));
                            spn2.setSelection(adapter.getPosition("USD"));
                        } else {
                            // Si la respuesta ded la API está vacía
                            Log.e("ERRORG", "(99) Api Response Null - vacía");
                        }
                    } else {
                        Toast.makeText(DivisasActivity.this, "Error al obtener datos de moneda", Toast.LENGTH_SHORT).show();
                        Log.e("ERRORG", "(104) Respuesta sin éxito. Código: " + response.code());
                        try {
                            // try para "intentar" traer la descripción del código
                            String errorBody = response.errorBody().toString();
                            Log.e("DebugGabriel", "(108) Cuerpo de respuesta de error: " + errorBody);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e("ERRORG", "(116) Excepción durante llamada a API: " + e.getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<ApiResponse> call, @NonNull Throwable t) {
//                Cierro el ProgressDialog si hay un fallo en la llamada a la API
                progressDialog.dismiss();
                Toast.makeText(DivisasActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
                Log.e("ERRORG", "(123) Error de red durante la llamada a la API: " + t.getMessage());
            }
        });

        //Para poner títulos
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String code = spn1.getSelectedItem().toString();
                String nombre = obtenerNombre(code);
                txt1.setText(nombre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spn2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String code = spn2.getSelectedItem().toString();
                String nombre = obtenerNombre(code);
                txt2.setText(nombre);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    } //------------------------------------ FIN ONCREATE

    public void ClickRefrescar(View view){
        finish();
        startActivity(getIntent());
    }
    public void ClickMenu(View view) {
        //abrir barra
        MainActivity.abrirBarra(drawerLayout);
    }

    public void ClickLogo(View view) {
        //Cerrar barra
        MainActivity.cerrarBarra(drawerLayout);
    }

    public void ClickInicio(View view) {
        //Redirecciona activity al panel inicio
        MainActivity.redireccionarActivity(this, MainActivity.class);
    }

    public void ClickGastos(View view) {
        //Redirecciona activity al panel gastos
        MainActivity.redireccionarActivity(this, GastosActivity.class);
    }

    public void ClickDivisas(View view) {
        //recrea actividad
        recreate();
    }

    public void ClickConsultas(View view) {
        //Redirecciona activity al panel consultas
        MainActivity.redireccionarActivity(this, ConsultasActivity.class);
    }

    public void ClickLogout(View view) {
            MainActivity.cerrarSesion(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //cierra barra
        MainActivity.cerrarBarra(drawerLayout);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        if (etx1.isFocused()) {
            // Si están escribiendo en etx1 se hace la conversión a etx2
            convertirDivisa(etx1, etx2, spn1, spn2);
        } else if (etx2.isFocused()) {
            // Si están escribiendo en etx2 ahora se escriba la conversión en etx1
            convertirDivisa(etx2, etx1, spn2, spn1);
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn) {
            // obtengo los códigos actuales
            String code1 = spn1.getSelectedItem().toString();
            String code2 = spn2.getSelectedItem().toString();
            spn1.setSelection(((ArrayAdapter<String>) spn1.getAdapter()).getPosition(code2));
            spn2.setSelection(((ArrayAdapter<String>) spn2.getAdapter()).getPosition(code1));

            // convertimos
            convertirDivisa(etx1, etx2, spn1, spn2);
        }
    }

    @SuppressLint("StaticFieldLeak")
    private void convertirDivisa(final EditText origen, final EditText destino, final Spinner spOrigen, final Spinner spDestino) {
        try {
            final double cantidadIngresada = Double.parseDouble(origen.getText().toString());

            // Tarea asíncrona para evitar la sobrecarga en el hilo principal
            new AsyncTask<Void, Void, Double>() {

                @Override
                protected Double doInBackground(Void... voids) {
                    String codigoOrigen = spOrigen.getSelectedItem().toString(); //De
                    String codigoDestino = spDestino.getSelectedItem().toString(); //a

                    //Buscamos valores equivalentes a 1 dólar de cada divisa
                    CurrencyData monedaOrigen = apiResponse.getData().get(codigoOrigen);
                    CurrencyData monedaDestino = apiResponse.getData().get(codigoDestino);

                    //Si tenemos todos nuestros datos necesarios
                    if (apiResponse != null && monedaOrigen != null && monedaDestino != null) {
                        // Asigno  a vars double lo obtenido de "value" del "data" de JSON
                        double valorMonedaOrigen = monedaOrigen.getValue();
                        double valorMonedaDestino = monedaDestino.getValue();

                        //Ejemplo: 500 pesos argentinos a yenes japoneses
                        // 500 / 18.1762829644 = 25.5083747 * 149.3234579538 = 4,107.6465
                        return (cantidadIngresada / valorMonedaOrigen) * valorMonedaDestino;

                    }
                    // Devuelve null en caso de error
                    return null;
                }

                protected void onPostExecute(Double resultado) {
                    if (resultado != null) {
                        destino.setText(String.valueOf(resultado));
                    } else {
                        destino.setText("");
                        Log.d("DEBUG", origen + "vacío.");
                    }
                }
            }.execute();
        } catch (NumberFormatException e) {
            // Logs debug para ver errores pero salen si es vacío entonces lo quito
            // Manejo de excepciones si se ingresa una cantidad no válida
            // Log.w("WARNINGG", "(207) Number Format Exception.");
            Toast.makeText(DivisasActivity.this, "Ingrese una cantidad para realizar una conversión", Toast.LENGTH_SHORT).show();
        }
    }

    public static class ApiResponse {
        // Busca solo lo que esté dentro de "data"
        @SerializedName("data")
        private Map<String, CurrencyData> data;

        public ApiResponse(Map<String, CurrencyData> data) {
            this.data = data;
        }

        public Map<String, CurrencyData> getData() {
            // Retorno el resultado total de "data"
            return data;
        }
    }

    public static class CurrencyData {
        // Para regresar código "code" y valor "value"
        private String code; //Ej. ARS
        private double value; // Eje. 18.1762829644

        //Datos de la divisa
        public CurrencyData(String code, double value) {
            this.code = code;
            this.value = value;
        }

        // Retorno el código
        public String getCode() {
            return code;
        }

        // Retorno el valor
        public double getValue() {
            return value;
        }
    }

    // Interfaz
// Colección de métodos abstractos (sin implementación)
// Deben ser implementados para cualquier clase que implemente la interfaz
// Resumiendo, define un conjunto de operaciones que las clases concretas deben proporcionar
// Define un contrato para realizar solicitudes HTTP

    public interface ApiService {
        // Anotación de Retrofit para indicar el tipo de solicitud HTTP
        @GET("latest?apikey=" + API_KEY)
        Call<ApiResponse> getCurrencyData();
// Representa la solicitud HTTP enviada al servidor
// La respuesta se espera que sea un objeto de tipo ApiResponse
    }

    public String obtenerNombre(String code) {

        String nombre = null;
        if (code.isEmpty()) {
            nombre = "";
        } else if (code.equals("ADA")) {
            nombre = "Dirham de Emiratos Árabes Unidos";
        } else if (code.equals("AED")) {
            nombre = "Dirham de Dubái";
        } else if (code.equals("AFN")) {
            nombre = "Afgani afgano";
        } else if (code.equals("ALL")) {
            nombre = "Lek de Albania";
        } else if (code.equals("AMD")) {
            nombre = "Dram armenio";
        } else if (code.equals("ANG")) {
            nombre = "Florín holandés";
        } else if (code.equals("AOA")) {
            nombre = "Kwanza angoleño";
        } else if (code.equals("ARB")) {
            nombre = "Criptomenda Arbitrum";
        } else if (code.equals("ARS")) {
            nombre = "Peso argentino";
        } else if (code.equals("AUD")) {
            nombre = "Dólar australiano";
        } else if (code.equals("AVAX")) {
            nombre = "Avalanche";
        } else if (code.equals("AWG")) {
            nombre = "Florín arubeño";
        } else if (code.equals("AZN")) {
            nombre = "Manat azerbaiyano";
        } else if (code.equals("BAM")) {
            nombre = "Marco convertido de Bosnia";
        } else if (code.equals("BBD")) {
            nombre = "Dólar de Barbados";
        } else if (code.equals("BDT")) {
            nombre = "Taka de Bangladés";
        } else if (code.equals("BGN")) {
            nombre = "Lev búlgaro";
        } else if (code.equals("BHD")) {
            nombre = "Dinar bahreiní";
        } else if (code.equals("BIF")) {
            nombre = "Franco burundés";
        } else if (code.equals("BMD")) {
            nombre = "Dólar de Bermudas";
        } else if (code.equals("BNB")) {
            nombre = "Binance Coin";
        } else if (code.equals("BND")) {
            nombre = "Dólar de Brunéi";
        } else if (code.equals("BOB")) {
            nombre = "Boliviano boliviano";
        } else if (code.equals("BRL")) {
            nombre = "Real brasileño";
        } else if (code.equals("BSD")) {
            nombre = "Dólar bahameño";
        } else if (code.equals("BTC")) {
            nombre = "Bitcoin";
        } else if (code.equals("BTN")) {
            nombre = "Ngultrum butanés";
        } else if (code.equals("BWP")) {
            nombre = "Pula botsuano";
        } else if (code.equals("BYN")) {
            nombre = "Rublo bielorruso";
        } else if (code.equals("BYR")) {
            nombre = "Rublo bielorruso";
        } else if (code.equals("BZD")) {
            nombre = "Rublo bielorruso antiguo";
        } else if (code.equals("CAD")) {
            nombre = "Dólar canadiense";
        } else if (code.equals("CDF")) {
            nombre = "Franco congoleño";
        } else if (code.equals("CHF")) {
            nombre = "Franco suizo";
        } else if (code.equals("CLF")) {
            nombre = "Unidad de Fomento chilena";
        } else if (code.equals("CLP")) {
            nombre = "Peso chileno";
        } else if (code.equals("CNY")) {
            nombre = "Yuan chino";
        } else if (code.equals("COP")) {
            nombre = "Peso colombiano";
        } else if (code.equals("CRC")) {
            nombre = "Colón costarricense";
        } else if (code.equals("CUC")) {
            nombre = "Peso convertible cubano";
        } else if (code.equals("CUP")) {
            nombre = "Peso cubano";
        } else if (code.equals("CVE")) {
            nombre = "Escudo caboverdiano";
        } else if (code.equals("CZK")) {
            nombre = "Corona checa";
        } else if (code.equals("DAI")) {
            nombre = "Dai";
        } else if (code.equals("DJF")) {
            nombre = "Franco de Yibuti";
        } else if (code.equals("DKK")) {
            nombre = "Corona danesa";
        } else if (code.equals("DOP")) {
            nombre = "Peso dominicano";
        } else if (code.equals("DOT")) {
            nombre = "Polkadot";
        } else if (code.equals("DZD")) {
            nombre = "Dinar argelino";
        } else if (code.equals("EGP")) {
            nombre = "Libra egipcia";
        } else if (code.equals("ERN")) {
            nombre = "Nakfa eritreo";
        } else if (code.equals("ETB")) {
            nombre = "Birr etíope";
        } else if (code.equals("ETH")) {
            nombre = "Ethereum";
        } else if (code.equals("EUR")) {
            nombre = "Euro";
        } else if (code.equals("FJD")) {
            nombre = "Dólar fiyiano";
        } else if (code.equals("FKP")) {
            nombre = "Libra de las Islas Malvinas";
        } else if (code.equals("GBP")) {
            nombre = "Libra esterlina";
        } else if (code.equals("GEL")) {
            nombre = "Lari georgiano";
        } else if (code.equals("GGP")) {
            nombre = "Libra de Guernsey";
        } else if (code.equals("GHS")) {
            nombre = "Cedi ghanés";
        } else if (code.equals("GIP")) {
            nombre = "Libra de Gibraltar";
        } else if (code.equals("GMD")) {
            nombre = "Dalasi gambiano";
        } else if (code.equals("GNF")) {
            nombre = "Franco guineano";
        } else if (code.equals("GTQ")) {
            nombre = "Quetzal guatemalteco";
        } else if (code.equals("GYD")) {
            nombre = "Dólar guyanés";
        } else if (code.equals("HKD")) {
            nombre = "Dólar de Hong Kong";
        } else if (code.equals("HNL")) {
            nombre = "Lempira hondureño";
        } else if (code.equals("HRK")) {
            nombre = "Kuna croata";
        } else if (code.equals("HTG")) {
            nombre = "Gourde haitiano";
        } else if (code.equals("HUF")) {
            nombre = "Forint húngaro";
        } else if (code.equals("IDR")) {
            nombre = "Rupia indonesia";
        } else if (code.equals("ILS")) {
            nombre = "Nuevo séquel israelí";
        } else if (code.equals("IMP")) {
            nombre = "Libra de la Isla de Man";
        } else if (code.equals("INR")) {
            nombre = "Rupia india";
        } else if (code.equals("IQD")) {
            nombre = "Dinar iraquí";
        } else if (code.equals("IRR")) {
            nombre = "Rial iraní";
        } else if (code.equals("ISK")) {
            nombre = "Corona islandesa";
        } else if (code.equals("JEP")) {
            nombre = "Libra de Jersey";
        } else if (code.equals("JMD")) {
            nombre = "Dólar jamaiquino";
        } else if (code.equals("JOD")) {
            nombre = "Dinar jordano";
        } else if (code.equals("JPY")) {
            nombre = "Yen japonés";
        } else if (code.equals("KES")) {
            nombre = "Chelín keniano";
        } else if (code.equals("KGS")) {
            nombre = "Som kirguís";
        } else if (code.equals("KHR")) {
            nombre = "Riel camboyano";
        } else if (code.equals("KMF")) {
            nombre = "Franco comorense";
        } else if (code.equals("KPW")) {
            nombre = "Won norcoreano";
        } else if (code.equals("KRW")) {
            nombre = "Won surcoreano";
        } else if (code.equals("KWD")) {
            nombre = "Dinar kuwaiti";
        } else if (code.equals("KYD")) {
            nombre = "Dólar de las Islas Caimán";
        } else if (code.equals("KZT")) {
            nombre = "Tenge kazajo";
        } else if (code.equals("LAK")) {
            nombre = "Kip laosiano";
        } else if (code.equals("LBP")) {
            nombre = "Libra libanesa";
        } else if (code.equals("LKR")) {
            nombre = "Rupia de Sri Lanka";
        } else if (code.equals("LRD")) {
            nombre = "Dólar liberiano";
        } else if (code.equals("LSL")) {
            nombre = "Loti lesothiano";
        } else if (code.equals("LTC")) {
            nombre = "Litecoin";
        } else if (code.equals("LTL")) {
            nombre = "Litas lituana";
        } else if (code.equals("LVL")) {
            nombre = "Lats letón";
        } else if (code.equals("LYD")) {
            nombre = "Dinar libio";
        } else if (code.equals("MAD")) {
            nombre = "Dirham marroquí";
        } else if (code.equals("MATIC")) {
            nombre = "Polygon";
        } else if (code.equals("MDL")) {
            nombre = "Leu moldavo";
        } else if (code.equals("MGA")) {
            nombre = "Ariary malgache";
        } else if (code.equals("MKD")) {
            nombre = "Dinar macedonio";
        } else if (code.equals("MMK")) {
            nombre = "Kyat birmano";
        } else if (code.equals("MNT")) {
            nombre = "Tugrik mongol";
        } else if (code.equals("MOP")) {
            nombre = "Pataca de Macao";
        } else if (code.equals("MRO")) {
            nombre = "Ouguiya mauritano";
        } else if (code.equals("MRU")) {
            nombre = "Ouguiya mauritano";
        } else if (code.equals("MUR")) {
            nombre = "Rupia mauriciana";
        } else if (code.equals("MVR")) {
            nombre = "Rufiyaa maldiva";
        } else if (code.equals("MWK")) {
            nombre = "Kwacha malauí";
        } else if (code.equals("MXN")) {
            nombre = "Peso mexicano";
        } else if (code.equals("MYR")) {
            nombre = "Ringgit malayo";
        } else if (code.equals("MZN")) {
            nombre = "Metical mozambiqueño";
        } else if (code.equals("NAD")) {
            nombre = "Dólar namibio";
        } else if (code.equals("NGN")) {
            nombre = "Naira nigeriano";
        } else if (code.equals("NIO")) {
            nombre = "Córdoba nicaragüense";
        } else if (code.equals("NOK")) {
            nombre = "Corona noruega";
        } else if (code.equals("NPR")) {
            nombre = "Corona nepalesa";
        } else if (code.equals("NZD")) {
            nombre = "Dólar neozelandés";
        } else if (code.equals("OMR")) {
            nombre = "Rial omaní";
        } else if (code.equals("OP")) {
            nombre = "Rublo bielorruso";
        } else if (code.equals("PAB")) {
            nombre = "Balboa panameño";
        } else if (code.equals("PEN")) {
            nombre = "Sol peruano";
        } else if (code.equals("PGK")) {
            nombre = "Kina papú";
        } else if (code.equals("PHP")) {
            nombre = "Peso filipino";
        } else if (code.equals("PKR")) {
            nombre = "Pakistán";
        } else if (code.equals("PLN")) {
            nombre = "Zloty polaco";
        } else if (code.equals("PYG")) {
            nombre = "Guaraní paraguayo";
        } else if (code.equals("QAR")) {
            nombre = "Rial catarí";
        } else if (code.equals("RON")) {
            nombre = "Leru rumano";
        } else if (code.equals("RSD")) {
            nombre = "Dinar serbio";
        } else if (code.equals("RUB")) {
            nombre = "Rublo ruso";
        } else if (code.equals("RWF")) {
            nombre = "Franco ruandés";
        } else if (code.equals("SAR")) {
            nombre = "Riyal saudí";
        } else if (code.equals("SBD")) {
            nombre = "Dólar de las Islas Salomón";
        } else if (code.equals("SCR")) {
            nombre = "Rupia seychellense";
        } else if (code.equals("SDG")) {
            nombre = "Libra sudanesa";
        } else if (code.equals("SEK")) {
            nombre = "Corona sueca";
        } else if (code.equals("SGD")) {
            nombre = "Dólar de Singapur";
        } else if (code.equals("SHP")) {
            nombre = "Libra de Santa Elena";
        } else if (code.equals("SLL")) {
            nombre = "Leone sierraleonés";
        } else if (code.equals("SOL")) {
            nombre = "Sol peruano";
        } else if (code.equals("SOS")) {
            nombre = "Chelín somalí";
        } else if (code.equals("SRD")) {
            nombre = "Dólar surinamés";
        } else if (code.equals("STD")) {
            nombre = "Dobra de Santo Tomé y Príncipe";
        } else if (code.equals("STN")) {
            nombre = "Dobra de Santo Tomé y Príncipe";
        } else if (code.equals("SVC")) {
            nombre = "Colón salvadoreño";
        } else if (code.equals("SYP")) {
            nombre = "Libra siria";
        } else if (code.equals("SZL")) {
            nombre = "Lilangeni suazi";
        } else if (code.equals("THB")) {
            nombre = "Baht tailandés";
        } else if (code.equals("TJS")) {
            nombre = "Somoni tayiko";
        } else if (code.equals("TMT")) {
            nombre = "Manat turcomano";
        } else if (code.equals("TND")) {
            nombre = "Dinar tunecino";
        } else if (code.equals("TOP")) {
            nombre = "Pa'anga tongano";
        } else if (code.equals("TRY")) {
            nombre = "Lira turca";
        } else if (code.equals("TTD")) {
            nombre = "Dólar de Trinidad y Tobago";
        } else if (code.equals("TWD")) {
            nombre = "Nuevo dólar taiwanés";
        } else if (code.equals("TZS")) {
            nombre = "Chelín tanzano";
        } else if (code.equals("UAH")) {
            nombre = "Grivna ucraniana";
        } else if (code.equals("UGX")) {
            nombre = "Chelín ugandés";
        } else if (code.equals("USD")) {
            nombre = "Dólar estadounidense";
        } else if (code.equals("USDC")) {
            nombre = "USD Coin";
        } else if (code.equals("USDT")) {
            nombre = "Tether";
        } else if (code.equals("UYU")) {
            nombre = "Peso uruguayo";
        } else if (code.equals("UZS")) {
            nombre = "Som uzbeko";
        } else if (code.equals("VEF")) {
            nombre = "Bolivar venezolano";
        } else if (code.equals("VES")) {
            nombre = "Bolivar soberano venezolano";
        } else if (code.equals("VND")) {
            nombre = "Dong vietnamita";
        } else if (code.equals("VUV")) {
            nombre = "Vatu vanuatuense";
        } else if (code.equals("WST")) {
            nombre = "Tala samoano";
        } else if (code.equals("XAF")) {
            nombre = "Franco CFA de África Central";
        } else if (code.equals("XAG")) {
            nombre = "Plata";
        } else if (code.equals("XAU")) {
            nombre = "Oro";
        } else if (code.equals("XCD")) {
            nombre = "Dólar del Caribe Oriental";
        } else if (code.equals("XDR")) {
            nombre = "Derechos Especiales de Giro";
        } else if (code.equals("XOF")) {
            nombre = "Franco CFA de África Occidental";
        } else if (code.equals("XPD")) {
            nombre = "Paladio";
        } else if (code.equals("XPF")) {
            nombre = "Franco CFP";
        } else if (code.equals("XPT")) {
            nombre = "Platino";
        } else if (code.equals("XRP")) {
            nombre = "Ripple";
        } else if (code.equals("YER")) {
            nombre = "Rial yemení";
        } else if (code.equals("ZAR")) {
            nombre = "Rand sudafricano";
        } else if (code.equals("ZMK")) {
            nombre = "Kwacha zambiano";
        } else if (code.equals("ZMW")) {
            nombre = "Kwacha zambiano";
        } else if (code.equals("ZML")) {
            nombre = "Dólar zimbabuense";
        }
        return nombre;
    }


    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}