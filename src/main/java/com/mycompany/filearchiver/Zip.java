package com.mycompany.filearchiver;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JProgressBar;

public class Zip {
    
    public static void zip(String savePath, List<File> saveFiles, JProgressBar progressBar) {
        try {
            BufferedInputStream origin = null;
            FileOutputStream dest = new FileOutputStream(savePath + ".zip"); // path al guardar
            ZipOutputStream out = new ZipOutputStream(new BufferedOutputStream(dest));
            byte[] data = new byte[1024];
            Iterator i = saveFiles.iterator();
            
            int c = 0;
            while (i.hasNext()) {
                c++;                
                File filename = (File) i.next();
                String path = filename.getPath();
                FileInputStream fi = new FileInputStream(path);
                origin = new BufferedInputStream(fi, 1024);
                ZipEntry entry = new ZipEntry(filename.getName());
                out.putNextEntry(entry);
                
                int count;
                while ((count = origin.read(data, 0, 1024)) != -1) {
                    out.write(data, 0, count);
                }
                progressBar.setValue(((c * 100) / saveFiles.size()));
                origin.close();
            }
            out.close();
        }catch( IOException e){}
    }
    
}
