
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

import java.awt.*;
import java.io.*;

//以繼承JInternalFrame類別的方式定義編輯文字的內部框架類別
class TextInternalFrame extends JInternalFrame {

    JTextPane tpContent = new JTextPane(); //宣告文字編輯面版
    String strFilePath = "C:\\新文字文件.txt"; //編輯檔案的預設檔案路徑
    String strFileName = "新文字文件.txt"; //編輯檔案的預設檔案名稱
    boolean isNew = true, //判別編輯檔案是否為新開啟檔案的布林值
            changed = false; //判別編輯檔案內容是否改變的布林值
    JCheckBoxMenuItem cbmi; //代表內部框架的核取方塊選項,
    //將加入至MDIEditor所建立視窗框架的視窗功能表

    int fontSize = 16; //設定字級大小為16

    TextInternalFrame(String... strArgs) { //建構子

        super(null, true, true, true, true); //呼叫基礎類別的建構子

        getContentPane().add(new JScrollPane(tpContent));
        //以文字編輯面版建立捲軸面版然後加入內部框架的內容面版

        if (strArgs.length != 0) { //判斷傳入參數個數是否為0
            strFilePath = strArgs[0]; //設定檔案路徑與名稱
            strFileName = strArgs[1]; //設定檔案名稱
            isNew = false; //設定不為新開啟的檔案

            try {
                tpContent.read(new FileReader(strFilePath), null);
                //以傳入的檔案路徑名稱宣告FileReader物件
            } catch (IOException ioe) {
                System.err.println("檔案讀取發生問題");
            }
        }

        Document doc = tpContent.getDocument(); //取得文字編輯面版的Document物件
        doc.addDocumentListener(dl); //註冊Document物件監聽器

        setTitle(strFileName); //設定內部框架的標題

        cbmi = new JCheckBoxMenuItem(strFileName);
        //以內部框架編輯之檔案的名稱宣告核取方塊選項

        tpContent.setFont(new Font("Times-Roman", Font.BOLD, fontSize));
        //設定文字編輯面版使用的字型

        setSize(300, 200); //設定內部視窗框架的大小

        setVisible(true); //設定顯示內部視窗框架
    }

    //註冊回應CaretEvent事件的監聽器
    public void addCaretListener(CaretListener cl) {
        tpContent.addCaretListener(cl);
    }

    //關閉內部框架前所呼叫的方法
    @Override
    public void doDefaultCloseAction() {
        //判斷內部框架編輯的檔案是否改變, 並顯示確認方塊要求使用者決定是否儲存
        if (changed == true
                && JOptionPane.showConfirmDialog(this, "檔案尚未儲存要關閉嗎?",
                        "訊息", JOptionPane.YES_NO_OPTION,
                        JOptionPane.INFORMATION_MESSAGE) == JOptionPane.NO_OPTION) {
            return;
        }
        super.doDefaultCloseAction(); //呼叫基礎類別的方法
    }

    public boolean isNew() {
        return isNew;
    }
    //取得內部框架編輯檔案是否為新增

    public boolean isChanged() {
        return changed;
    }
    //取得內部框架編輯檔案是否改變

    public void setFileName(String name) { //設定內部框架編輯檔案的名稱
        setTitle(name); //更新內部框架為編輯檔案的名稱
        cbmi.setText(name); //更新核取方塊選項顯示的檔案名稱
        strFileName = name; //更新屬性設定
    }

    public void setFilePath(String path) {
        strFilePath = path;
    } //設定檔案路徑

    public String getFileName() {
        return strFileName;
    } //取得檔案名稱

    public String getFilePath() {
        return strFilePath;
    } //取得檔案路徑

    public JTextPane getTextPane() {
        return tpContent;
    } //取得文字編輯面版

    //設定字級大小
    public void setFontSize(int size) {
        tpContent.setFont(new Font("Times-Roman", Font.BOLD, size));
        //設定文字編輯面版使用的字級大小

        fontSize = size; //更新屬性
    }

    public int getFontSize() {
        return fontSize;
    } //取得使用的字級大小

    //將內部框架編輯的內容輸出至Writer
    public void write(Writer out) throws IOException {
        changed = false; //更新屬性表示編輯檔案變更的內容已經儲存
        tpContent.write(out); //輸出編輯內容
    }

    public JCheckBoxMenuItem getMenuItem() {
        return cbmi;
    }
    //取得代表內部框架的核取方塊選項
    //定義並宣告回應DocumentEvent事件的監聽器
    DocumentListener dl = new DocumentListener() {
        @Override
        public void changedUpdate(DocumentEvent e) {
        }

        //回應資料新增動作
        @Override
        public void insertUpdate(DocumentEvent e) {
            changed = true;
        }

        //回應資料移除動作
        @Override
        public void removeUpdate(DocumentEvent e) {
            changed = true;
        }
    };
}
