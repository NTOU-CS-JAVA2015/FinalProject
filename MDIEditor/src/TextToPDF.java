
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import java.io.BufferedReader;
import java.io.File;

import java.io.FileOutputStream;
import java.io.FileReader;
import javax.swing.JFileChooser;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Chen
 */

 
 
public class TextToPDF {
    public static void readFile(File file)
    {

    }
    public static void convert(File file) throws DocumentException
    {
        Document document = new Document();
        document.setPageSize(PageSize.A4);
        String fileName = file.getName().replace(".txt", ".pdf");
      
        FileReader reader = null;
        try{
            reader = new FileReader(file.getAbsolutePath());
        }catch(Exception e){
            System.out.println("檔案開啟錯誤");
            System.out.println(e);
            System.exit(-1);
        }
        
        try{
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            
            document.open();
            //設定文件標題 start
            String title = file.getName().replace(".txt","");
            BaseFont bfChinese = BaseFont.createFont("C:\\windows\\fonts\\KAIU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(bfChinese);
            font.setColor(BaseColor.DARK_GRAY);
            Paragraph paragraph = new Paragraph(title, font);
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            //設定文件標題 end
            
            paragraph.setAlignment(Element.ALIGN_LEFT);
            
            BufferedReader text = new BufferedReader(reader);
            
            String line;
            while( (line = text.readLine()) != null)
            {
               
               paragraph.add(line);
               paragraph.add("\n");
            }
            
            document.add(paragraph);
            
            document.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String args [] )
    {
        JFileChooser chooser = new JFileChooser();
        //產生一個檔案選擇器
        chooser.setDialogTitle("Select TXT file");
        //檔案選擇器的Title  
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        chooser.setFileFilter(new FileFilter_PDF());
        chooser.setAcceptAllFileFilterUsed(false);
        //設定檔案的過濾:只允許TXT檔案
        int response = chooser.showOpenDialog(null);
        
        if(response == JFileChooser.APPROVE_OPTION){
            File file = chooser.getSelectedFile();
            String fileName = file.getAbsolutePath();
            System.out.println(fileName);
            try{
                TextToPDF.convert(file);
            }catch(Exception e){
                System.out.println(e);
            }
        }
    }
}
