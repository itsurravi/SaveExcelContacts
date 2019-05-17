package com.ravisharma.saveexcelcontacts;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileSelectActivity extends AppCompatActivity {

    private FileArrayAdapter adapter;
    private File currentDir;
    private ArrayList<String> extensions;
    private FileFilter fileFilter;
    private File fileSelected;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file_select);

        listView = (ListView) findViewById(R.id.listViewFile);

        Bundle extras = getIntent().getExtras();
        if (!(extras == null || extras.getStringArrayList("filterFileExtension") == null)) {
            extensions = extras.getStringArrayList("filterFileExtension");
            fileFilter = new FileFilter() {
                public boolean accept(File pathname) {
                    return pathname.isDirectory() || (pathname.getName().contains(".") &&
                            extensions.contains(pathname.getName().substring(pathname.getName().lastIndexOf("."))));
                }
            };
        }
        currentDir = new File(Environment.getExternalStorageDirectory() + "/");
        fill(currentDir);

    }

    private void fill(File f) {
        File[] dirs;
        if (this.fileFilter != null) {
            dirs = f.listFiles(this.fileFilter);
        } else {
            dirs = f.listFiles();
        }
        setTitle(getString(R.string.currentDir) + ": " + f.getName());
        List<Option> dir = new ArrayList();
        List<Option> fls = new ArrayList();
        try {
            for (File ff : dirs) {
                if (ff.isDirectory() && !ff.isHidden()) {
                    dir.add(new Option(ff.getName(), getString(R.string.folder), ff.getAbsolutePath(), true, false));
                } else if (!ff.isHidden()) {
                    fls.add(new Option(ff.getName(), getString(R.string.fileSize) + ": " + ff.length(), ff.getAbsolutePath(), false, false));
                }
            }
        } catch (Exception e) {

        }

        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if (!(f.getName().equalsIgnoreCase("sdcard") || f.getParentFile() == null)) {
            dir.add(0, new Option("..", getString(R.string.parentDirectory), f.getParent(), false, true));
        }
        adapter = new FileArrayAdapter(getApplicationContext(), R.layout.file_view, dir);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                Option o = adapter.getItem(position);
                if (o.isFolder() || o.isParent()) {
                    currentDir = new File(o.getPath());
                    fill(currentDir);
                    return;
                }
                fileSelected = new File(o.getPath());
                if (fileSelected.getName().contains(".xls") || fileSelected.getName().contains(".xlsx")) {
                    Intent intent = new Intent(getApplicationContext(), ViewContactsActivity.class);
                    intent.putExtra("fileSelected", fileSelected.getAbsolutePath());
                    startActivity(intent);
                    finish();
                    return;
                }
                Toast.makeText(FileSelectActivity.this, "Please choose excel file (.XLS)", Toast.LENGTH_LONG).show();
            }
        });
    }
}
