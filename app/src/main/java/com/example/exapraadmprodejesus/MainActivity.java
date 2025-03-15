package com.example.exapraadmprodejesus;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText codigo, nombre, puesto, diasTrabajados;
    public TextView etfinal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        codigo =findViewById(R.id.etCodigo);
        nombre =findViewById(R.id.etNombre);
        puesto =findViewById(R.id.etPuesto);
        diasTrabajados =findViewById(R.id.etDiasTr);
        etfinal =findViewById(R.id.etFinal);

    }
    public void altaEmpleados(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase(); // Database object is writable

        // Capture values from form
        String Codigo = codigo.getText().toString();
        String Nombre = nombre.getText().toString();
        String Puesto = puesto.getText().toString();
        String DiasTrabajados = diasTrabajados.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("cod", Codigo);
        registro.put("nombre", Nombre);
        registro.put("puesto", Puesto);
        registro.put("diasTrabajados", DiasTrabajados);

        bd.insert("empleados", null, registro);

        bd.close();

        codigo.setText(null);
        nombre.setText(null);
        puesto.setText(null);
        diasTrabajados.setText(null);
        this.etfinal.setText("");
        Toast.makeText(this, "Éxito al ingresar el registro\n\nCódigo: " + Codigo + "\nNombre: " + Nombre + "\nPuesto: " + Puesto + "\nDías Trabajados: " + DiasTrabajados, Toast.LENGTH_LONG).show();
    }

    public void consultaEmpleados(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();


        String codigoConsulta = codigo.getText().toString();


        Cursor fila = bd.rawQuery("SELECT nombre, puesto, diasTrabajados FROM empleados WHERE cod = ?", new String[]{codigoConsulta});

        if (fila.moveToFirst()) {
            nombre.setText(fila.getString(0));
            puesto.setText(fila.getString(1));
            diasTrabajados.setText(fila.getString(2));


            int diasTrabajadosInt = 0;
            try {
                diasTrabajadosInt = Integer.parseInt(fila.getString(2));
            } catch (NumberFormatException e) {

                Toast.makeText(this, "Error al procesar los días trabajados", Toast.LENGTH_LONG).show();
            }


            if (diasTrabajadosInt >= 15) {
                double bonus = (diasTrabajadosInt * 255) * 0.15;
                etfinal.setText(String.valueOf(bonus));

            }
        }
    }
    public void borrarEmpleados(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se asigna variable para busqueda por campo distitivo caodigo producto
        String codigoBaja = codigo.getText().toString();
        //Se genera instrtuccion SQL para que se elimine el registro de producto
        int c = bd.delete("empleados","cod="+codigoBaja,null);
        if(c==1){
            Toast.makeText(this,"Registro eliminado de BD exitoso\nVerifica Consulta",Toast.LENGTH_LONG).show();
            //Limpia cajas de texto
            this.codigo.setText("");
            this.nombre.setText("");
            this.puesto.setText("");
            this.diasTrabajados.setText("");
            this.etfinal.setText("");
        }else{
            Toast.makeText(this,"Error\nNo existe Articulo con ese codigo",Toast.LENGTH_LONG).show();
        }
    }
    public void editarEmpleados(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase bd = admin.getWritableDatabase();//objetos de base de datos  reescribible

        //se declaran variables que vienen desde formulario sus datos
        String Codigo = codigo.getText().toString();
        String Nombre = nombre.getText().toString();
        String Puesto = puesto.getText().toString();
        String DiasTrabajados = diasTrabajados.getText().toString();

        //se genera un contenedor para almacenar los valores anteriores
        ContentValues registro = new ContentValues();
        registro.put("cod",Codigo);
        registro.put("nombre",Nombre);
        registro.put("puesto",Puesto);
        registro.put("diasTrabajados",DiasTrabajados);
        //Limpia cajas de texto
        this.codigo.setText("");
        this.nombre.setText("");
        this.puesto.setText("");
        this.diasTrabajados.setText("");
        this.etfinal.setText("");
        //Se crea la variable que contine la instruccion SQL encargada de modificar y almacenar valor 1 si edito
        int cant = bd.update("empleados",registro,"cod="+Codigo,null);
        bd.close();
        if(cant==1) {//condicion si realizo modificacion
            Toast.makeText(this,"Registro actualizado de forma correcta",Toast.LENGTH_LONG).show();
        }else {//contrario a no modificacion
            Toast.makeText(this,"Error\nNo se modifico registro",Toast.LENGTH_LONG).show();
        }
    }
}