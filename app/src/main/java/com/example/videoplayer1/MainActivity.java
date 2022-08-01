package com.example.videoplayer1;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.videoplayer1.videoplayer.CustomAdapter;
import com.example.videoplayer1.videoplayer.SelectListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements SelectListener {

    RecyclerView recyclerView;
    List<File> fileListl;
    File path = new File(System.getenv("EXTERNAL_STORAGE"));
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        askPermission();
    }

    private void askPermission() {
        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        displayFiles();

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                        Toast.makeText(MainActivity.this, "Quyền truy cập bị từ chối", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();
    }

    private void displayFiles() {
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        fileListl =new ArrayList<>();
        fileListl.addAll(findVideos(path));

        customAdapter = new CustomAdapter(this, fileListl, this);
        customAdapter.setHasStableIds(true);

        recyclerView.setAdapter(customAdapter);
    }

    private ArrayList<File> findVideos(File file){
        ArrayList<File> myVideos = new ArrayList<>();
        File[] allFiles = file.listFiles();

        for (File singleFile : allFiles){
            if(singleFile.isDirectory() && !singleFile.isHidden()){
                myVideos.addAll(findVideos(singleFile));
            }

            else if(singleFile.getName().toLowerCase().endsWith(".mp4")){
                myVideos.add(singleFile);
            }
        }
        return myVideos;
    }

    @Override
    public void onFileClicked(File file) {
            Intent intent = new Intent(this, PlayerActivity.class);
            Bundle bundle =new Bundle();
//            intent.putExtra("Video", file);
            bundle.putString("Video", String.valueOf(file.getAbsoluteFile()));
            intent.putExtras(bundle);
        startActivity(intent);
    }
}
