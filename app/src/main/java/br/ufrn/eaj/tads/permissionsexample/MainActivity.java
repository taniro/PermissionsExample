package br.ufrn.eaj.tads.permissionsexample;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


/*
* @author ricardo lecheta, modified by taniro
 */

public class MainActivity extends AppCompatActivity {

    // Solicita as permissões
    final String[] permissoes = new String[]{
            Manifest.permission.CALL_PHONE,
            Manifest.permission.READ_CONTACTS,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String[] items = new String[]{
                "Ligar para telefone",
                "Discar para telefone",

                "Enviar E-mail",
                "Enviar SMS",
                "Abrir Browser",

                "Mapa - Lat/Lng",
                "Mapa - Endereco",
                "Mapa - Rota",

                "Compartilhar",

                "Camera Foto",
                "Camera Vídeo",

                "Intent customizada",
                "Browser customizado",

                "Sair"

        };

        ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @SuppressWarnings("MissingPermission")
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                try {
                    switch (position) {
                        case 0:
                            Uri uri = Uri.parse("tel:(84)98888-1234");
                            Intent intent = new Intent(Intent.ACTION_CALL, uri);
                            startActivity(intent);
                            break;
                        case 1:
                            uri = Uri.parse("tel:(84)98888-1234");
                            intent = new Intent(Intent.ACTION_DIAL, uri);
                            startActivity(intent);
                            break;
                        case 2:
                            // Email
                            Intent emailIntent = new Intent(Intent.ACTION_SEND);
                            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Título do Email");
                            emailIntent.putExtra(Intent.EXTRA_TEXT, "Mensagem do Email");
                            emailIntent.putExtra(Intent.EXTRA_EMAIL, "tanirocr@gmail.com");
                            emailIntent.setType("message/rfc822");
                            startActivity(emailIntent);
                            break;
                        case 3:
                            // SMS
                            uri = Uri.parse("sms:(84)98888-1234");
                            Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
                            smsIntent.putExtra("sms_body", "Olá, isso é uma mensagem :)");
                            startActivity(smsIntent);
                            break;

                        case 4:
                            // Browser
                            uri = Uri.parse("http://google.com");
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            break;
                        case 5:
                            // Mapa
                            String GEO_URI = "geo:-5.8841826,-35.365046?q=(EAJ)";
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI));
                            startActivity(intent);
                            break;
                        case 6:
                            // Mapa
                            GEO_URI = "geo:0,0?q=UFRN";
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(GEO_URI));
                            startActivity(intent);
                            break;

                        case 7:
                            // Rota
                            String rota = "http://maps.google.com/maps?saddr=-5.8841826,-35.365046&daddr=-5.8579104,-35.3558422";
                            intent = new Intent(Intent.ACTION_VIEW, Uri.parse(rota));
                            startActivity(intent);
                            break;

                        case 8:
                            // Compartilhar
                            Intent shareIntent = new Intent(Intent.ACTION_SEND);
                            shareIntent.setType("text/plain");
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Compartilhar");
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Bla bla bla");
                            startActivity(shareIntent);
                            break;

                        case 9:
                            // Tirar foto
                            // "android.media.action.IMAGE_CAPTURE
                            Intent fotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(fotoIntent, 9);
                            break;

                        case 10:
                            // Gravar Vídeo
                            // android.media.action.VIDEO_CAPTURE
                            Intent videoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            startActivityForResult(videoIntent, 0);
                            break;

                        case 11:
                            // INTENT_FILTER
                            intent = new Intent("tads.eaj.com.intentexampleb.TESTE");
                            startActivity(intent);
                            break;
                        case 12:
                            // INTENT_FILTER
                            uri = Uri.parse("http://tads.eaj.ufrn.br");
                            intent = new Intent(Intent.ACTION_VIEW, uri);
                            startActivity(intent);
                            break;

                        default:
                            finish();
                            break;
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Erro :" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Alguma permissão foi negada, agora é com você :-)
                alertAndFinish();
                return;
            }
        }
        // Se chegou aqui está OK :-)
    }

    @Override
    protected void onActivityResult(int codigo, int resultado, Intent it) {

        if (codigo == 9 && resultado == RESULT_OK) {
            Bundle bundle = it.getExtras();
            if (bundle != null) {
                // Recupera o Bitmap retornado pela câmera
                Bitmap bitmap = (Bitmap) bundle.get("data");

                showToastImageView(bitmap);
            }
        }
    }

    private void alertAndFinish() {
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.app_name).setMessage("Para utilizar este aplicativo, você precisa aceitar as permissões.");
            // Add the buttons
            builder.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            builder.setPositiveButton("Permitir", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }
            });
            android.support.v7.app.AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void showToastImageView(Bitmap bitmap) {
        Toast t = new Toast(this);
        ImageView imgView = new ImageView(this);
        imgView.setImageBitmap(bitmap);
        t.setView(imgView);
        t.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        PermissionUtils.validate(this, 0, permissoes);
    }
}
