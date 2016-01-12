
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chen
 */

public class FileFilter_PDF extends FileFilter{

    @Override
    public boolean accept(File file) {
        if(file.getName().toLowerCase().endsWith(".txt")){
            return true;
        }
        return false;
    }

    @Override
    public String getDescription() {
        return "Txt Files";
    }
    
        
       
}

