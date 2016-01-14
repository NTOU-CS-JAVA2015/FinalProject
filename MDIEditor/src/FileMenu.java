
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author yuhang
 */
public class FileMenu extends JFrame {

    MDIEditor MDIEditor;
    AudioPlayer openYee = new AudioPlayer();//轉檔音效控制項
    java.net.URL Yee = MDIEditor.class.getResource("/voice/Yee.aiff");//取得Yee.aiff的URL

    FileMenu(JMenu mnFile, MDIEditor MDIEditorin) {
        MDIEditor = MDIEditorin;
        JMenuItem miNew = new JMenuItem("新增(N)", KeyEvent.VK_N),
                miOpen = new JMenuItem("開啟舊檔(O)", KeyEvent.VK_O),
                miSave = new JMenuItem("儲存檔案(S)", KeyEvent.VK_S),
                miSaveAn = new JMenuItem("另存新檔(A)", KeyEvent.VK_A),
                miYee = new JMenuItem("PDF轉TXT(Y)", KeyEvent.VK_Y),
                miToPDF = new JMenuItem("TXT轉PDF(T)", KeyEvent.VK_T),
                miExit = new JMenuItem("結束(E)", KeyEvent.VK_E);
        //宣告檔案功能表的選項

        miNew.addActionListener(alFile); //為功能表選項加上監聽器
        miOpen.addActionListener(alFile);
        miSave.addActionListener(alFile);
        miSaveAn.addActionListener(alFile);
        miYee.addActionListener(alFile);
        miToPDF.addActionListener(alFile);
        miExit.addActionListener(alFile);

        mnFile.add(miNew); //將選項加入檔案功能表
        mnFile.add(miOpen);
        mnFile.add(miSave);
        mnFile.add(miSaveAn);
        mnFile.addSeparator();
        mnFile.add(miYee);
        mnFile.add(miToPDF);
        mnFile.addSeparator();
        mnFile.add(miExit);
    }

    //定義並宣告回應檔案功能表內選項被選取所觸發事件的監聽器
    ActionListener alFile = (ActionEvent e) -> {
        int result;
        try {
            //執行檔案開啟動作
            switch (e.getActionCommand()) {
                case "開啟舊檔(O)": {
                    JFileChooser fcOpen = new JFileChooser(
                            MDIEditor.internalFrame.tifCurrent.getFilePath());
                    //宣告JFileChooser物件
                    FileFilter fileFilter = NewFileFilter("TXT File", new String[]{"txt"});
                    fcOpen.addChoosableFileFilter(fileFilter);
                    //設定篩選檔案的類型
                    fcOpen.setDialogTitle("開啟舊檔"); //設定檔案選擇對話盒的標題
                    result = fcOpen.showOpenDialog(MDIEditor);
                    //顯示開啟檔案對話盒
                    if (result == JFileChooser.APPROVE_OPTION) { //使用者按下 確認 按鈕
                        File file = fcOpen.getSelectedFile(); //取得選取的檔案
                        MDIEditor.internalFrame.createInternalFrame(file.getPath(), file.getName());
                        //以取得的檔案建立TextInternalFrame物件
                    }
                    break;
                }
                case "新增(N)":
                    //新增文件
                    MDIEditor.internalFrame.createInternalFrame(); //建立沒有內容的TextInternalFrame物件
                    break;
                case "儲存檔案(S)":
                    //執行儲存檔案動作
                    String strPath = MDIEditor.internalFrame.tifCurrent.getFilePath();
                    //取得目前TextInternalFrame物件開啟檔案的路徑與名稱
                    if (!MDIEditor.internalFrame.tifCurrent.isNew()) {
                        //判斷TextInternalFrame物件開啟的是否為新的檔案
                        FileWriter fw = new FileWriter(strPath);
                        //建立輸出檔案的FileWriter物件
                        MDIEditor.internalFrame.tifCurrent.write(fw);
                    } else {
                        MDIEditor.saveFile(strPath); //儲存檔案
                    }
                    break;
                case "另存新檔(A)":
                    MDIEditor.saveFile(MDIEditor.internalFrame.tifCurrent.getFilePath()); //儲存檔案
                    break;
                case "PDF轉TXT(Y)":
                    JFileChooser fcOpen = new JFileChooser(MDIEditor.internalFrame.tifCurrent.getFilePath());
                    //宣告JFileChooser物件 
                    FileFilter fileFilter = NewFileFilter("PDF Files", new String[]{"pdf"});
                    fcOpen.addChoosableFileFilter(fileFilter);
                    fcOpen.setFileSelectionMode(JFileChooser.FILES_ONLY);
                    //設定篩選檔案的類型
                    fcOpen.setAcceptAllFileFilterUsed(false);
                    fcOpen.setDialogTitle("選擇要轉檔的PDF"); //設定檔案選擇對話盒的標題
                    result = fcOpen.showOpenDialog(MDIEditor);
                    //顯示開啟檔案對話盒
                    if (result == JFileChooser.APPROVE_OPTION) { //使用者按下 確認 按鈕
                        File file = fcOpen.getSelectedFile(); //取得選取的檔案
                        try {
                            openYee.loadAudio(Yee);//載入yee
                        } catch (Exception YeeException) {
                            System.out.println(YeeException.toString());
                        }
                        openYee.play();
                        System.setProperty("apple.awt.UIElement", "true");
                        ExtractText extractor = new ExtractText();
                        String fi[] = {file.getPath()};
                        String fe, ff;
                        extractor.startExtraction(fi);
                        fe = fi[0].substring(0, fi[0].length() - 3) + "txt";//去尾
                        ff = file.getName().substring(0, file.getName().length() - 3) + "txt";//加上TXT
                        MDIEditor.internalFrame.createInternalFrame(fe, ff);//以取得的檔案建立TextInternalFrame物件
                    }
                    break;
                case "TXT轉PDF(T)":
                    TextToPDF textToPDF = new TextToPDF();
                    break;
                case "結束(E)":
                    processWindowEvent(
                            new WindowEvent(MDIEditor,
                                    WindowEvent.WINDOW_CLOSING));
                    //執行WindowEvent事件, 觸發MDIEditor視窗框架的關閉視窗事件
                    break;
            }
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        } catch (BadLocationException ble) {
            System.err.println("位置不正確\n" + ble.toString());
        }
    };

    /*public void saveFile(String strPath) //儲存檔案
            throws IOException, BadLocationException {

        int pos = strPath.lastIndexOf("\\");

        String path = strPath.substring(0, pos + 1);
        String name = strPath.substring(pos + 1, strPath.length());
        JFileChooser fcSave = new JFileChooser(path);  //建立檔案選取對話盒
        fcSave.setSelectedFile(new File(name)); //設定選取的檔案

        FileFilter fileFilter = NewFileFilter("TXT File", new String[]{"txt"});
        fcSave.addChoosableFileFilter(fileFilter);
        //設定篩選檔案的類型

        fcSave.setDialogTitle("另存新檔"); //設定對話盒標題

        int result = fcSave.showSaveDialog(MDIEditor);
        //顯示檔案儲存對話盒

        if (result == JFileChooser.APPROVE_OPTION) {
            //使用者按下 確認 按鈕

            File file = fcSave.getSelectedFile(); //取得選取的檔案
            MDIEditor.internalFrame.tifCurrent.write(new FileWriter(file));
            //將文字編輯內部框架的內容輸出至FileWriter物件

            MDIEditor.internalFrame.tifCurrent.setFileName(file.getName()); //設定編輯檔案名稱
            MDIEditor.internalFrame.tifCurrent.setFilePath(file.getPath()); //設定編輯檔案路徑
        }
    }
     */
    //建立過濾檔案選擇對話盒內檔案類型的物件
    public FileFilter NewFileFilter(final String desc, final String[] allowed_extensions) {
        return new FileFilter() {//建構子
            @Override
            public boolean accept(File f) {//若為資料夾傳回true
                if (f.isDirectory()) {
                    return true;
                }
                int pos = f.getName().lastIndexOf('.');//尋找檔案名稱內的"."號
                if (pos == -1) {
                    return false;
                } else {
                    String extension = f.getName().substring(pos + 1);//取得檔案名稱
                    for (String allowed_extension : allowed_extensions) {//從檔案名稱內取得副檔名字
                        if (extension.equalsIgnoreCase(allowed_extension)) {//判斷副檔名是否與檔案篩選物件的extension字串相同
                            return true;
                        }
                    }
                    return false;
                }
            }

            //傳回檔案篩選物件欲篩選檔案類型的描述字串
            @Override
            public String getDescription() {
                return desc;
            }
        };
    }

}
