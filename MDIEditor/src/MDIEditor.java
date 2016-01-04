//*************
// Nat Lee
//*************

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileFilter;

import java.io.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class MDIEditor extends JFrame {

    JDesktopPane dpPane = new JDesktopPane(); //容納內部框架的虛擬桌面
    TextInternalFrame tifCurrent; //目前點選的文字編輯內部框架

    WindowMenu wmWindow = new WindowMenu("視窗(W)", KeyEvent.VK_W);
    //控制內部視窗畫面切換的功能表

    JMenuItem miCut, miCopy, miPaste; //執行編輯動作的功能表選項
    JCheckBoxMenuItem cbmiSize16, cbmiSize18, cbmiSize20;
    //控制字級大小的核取方塊選項

    JToggleButton tbnSize16, tbnSize18, tbnSize20;
    //控制字級大小的工具列按鈕

    JLabel lbStatus; //顯示游標位置與選取字元的標籤
    Action acCut, acCopy, acPaste; //執行編輯動作的Action物件

    MDIEditor(String title) {
        super(title);//設定視窗名稱
        createInternalFrame(); //建立第一個內部框架

        JTextPane tpCurrent = tifCurrent.getTextPane(); //取得內部框架使用的文字編輯面版

        JMenu mnFile = new JMenu("檔案(F)"); //宣告檔案功能表
        mnFile.setMnemonic(KeyEvent.VK_F); //設定檔案功能表使用的記憶鍵

        JMenuItem miNew = new JMenuItem("新增(N)", KeyEvent.VK_N),
                miOpen = new JMenuItem("開啟舊檔(O)", KeyEvent.VK_O),
                miSave = new JMenuItem("儲存檔案(S)", KeyEvent.VK_S),
                miSaveAn = new JMenuItem("另存新檔(A)", KeyEvent.VK_A),
                miYee = new JMenuItem("PDF轉檔(Y)", KeyEvent.VK_Y),
                miExit = new JMenuItem("結束(E)", KeyEvent.VK_E);
        //宣告檔案功能表的選項

        miNew.addActionListener(alFile); //為功能表選項加上監聽器
        miOpen.addActionListener(alFile);
        miSave.addActionListener(alFile);
        miSaveAn.addActionListener(alFile);
        miYee.addActionListener(alFile);
        miExit.addActionListener(alFile);

        mnFile.add(miNew); //將選項加入檔案功能表
        mnFile.add(miOpen);
        mnFile.add(miSave);
        mnFile.add(miSaveAn);
        mnFile.add(miYee);
        mnFile.addSeparator();
        mnFile.add(miExit);

        JMenu mnEdit = new JMenu("編輯(E)"); //宣告編輯功能表
        mnEdit.setMnemonic(KeyEvent.VK_E); //設定編輯功能表的記憶鍵

        acCut = getActionByName(DefaultEditorKit.cutAction);
        acCopy = getActionByName(DefaultEditorKit.copyAction);
        acPaste = getActionByName(DefaultEditorKit.pasteAction);
        //取得JTextPane元件提供執行剪下、複製、貼上動作的Action物件

        acCut.putValue(Action.NAME, "剪下(T)"); //設定Action物件使用的名稱
        acCopy.putValue(Action.NAME, "複製(C)");
        acPaste.putValue(Action.NAME, "貼上(P)");

        acCut.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_T);
        acCopy.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_C);
        acPaste.putValue(Action.MNEMONIC_KEY, KeyEvent.VK_P);
        //設定Action物件使用的記憶鍵

        acCut.setEnabled(false); //設定Action物件無效
        acCopy.setEnabled(false);

        mnEdit.add(acCut); //將Action物件加入功能表做為選項
        mnEdit.add(acCopy);
        mnEdit.add(acPaste);

        JMenu mnFontSize = new JMenu("字級(S)"); //宣告字級功能表
        mnFontSize.setMnemonic(KeyEvent.VK_S); //設定字級功能表的記憶鍵

        FontSizeAction fsaSize16 = new FontSizeAction(
                "16(S)", new ImageIcon("icon/size16.png"),
                "設定字體大小為16", KeyEvent.VK_S),
                fsaSize18 = new FontSizeAction(
                        "18(M)", new ImageIcon("icon/size18.png"),
                        "設定字體大小為18", KeyEvent.VK_M),
                fsaSize20 = new FontSizeAction(
                        "20(L)", new ImageIcon("icon/size20.png"),
                        "設定字體大小為20", KeyEvent.VK_L);
        //宣告執行字級大小設定動作的Action物件

        cbmiSize16 = new JCheckBoxMenuItem(fsaSize16);
        cbmiSize18 = new JCheckBoxMenuItem(fsaSize18);
        cbmiSize20 = new JCheckBoxMenuItem(fsaSize20);
        //以執行字級大小設定之Action物件建立核取方塊選項

        cbmiSize16.setIcon(null); //設定核取方塊選項不使用圖示
        cbmiSize18.setIcon(null);
        cbmiSize20.setIcon(null);

        cbmiSize16.setState(true); //設定選取代表16字級的核取方塊選項

        mnFontSize.add(cbmiSize16); //將核取方塊選項加入功能表
        mnFontSize.add(cbmiSize18);
        mnFontSize.add(cbmiSize20);

        ButtonGroup bgSize = new ButtonGroup(); //宣告按鈕群組
        bgSize.add(cbmiSize16); //將核取方塊選項加入按鈕群組
        bgSize.add(cbmiSize18);
        bgSize.add(cbmiSize20);

        JMenu mnAbout = new JMenu("關於(R)"); //宣告關於
        mnAbout.setMnemonic(KeyEvent.VK_R); //設定檔案功能表使用的記憶鍵
        JMenuItem miIntroduce = new JMenuItem("Team Member"),
                miNatLee = new JMenuItem("00181034 李映澤"),
                miYuHang = new JMenuItem("00257122 張語航"),
                miFinianrry = new JMenuItem("00257138 吳彥澄"),
                miTommy = new JMenuItem("00257141 陳平揚"),
                miVic = new JMenuItem("00257148 陳威任");

        miNatLee.addActionListener(about);
        miYuHang.addActionListener(about);
        miFinianrry.addActionListener(about);
        miTommy.addActionListener(about);
        miVic.addActionListener(about);

        mnAbout.add(miIntroduce);
        mnAbout.addSeparator();
        mnAbout.add(miNatLee);
        mnAbout.add(miYuHang);
        mnAbout.add(miFinianrry);
        mnAbout.add(miTommy);
        mnAbout.add(miVic);

        JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
        setJMenuBar(jmb); //設定視窗框架使用的功能表列
        jmb.add(mnFile); //將功能表加入功能表列
        jmb.add(mnEdit);
        jmb.add(mnFontSize);
        jmb.add(wmWindow);
        jmb.add(mnAbout);

        JToolBar tbFontSize = new JToolBar(); //新增工具列

        tbnSize16 = new JToggleButton(fsaSize16);
        tbnSize18 = new JToggleButton(fsaSize18);
        tbnSize20 = new JToggleButton(fsaSize20);
        //以執行字級大小設定的Action物件, 宣告工具列的JToggleButton按鈕

        tbFontSize.add(tbnSize16); //將JToggleButton按鈕加入工具列
        tbFontSize.add(tbnSize18);
        tbFontSize.add(tbnSize20);

        tbnSize16.setActionCommand("16(S)");
        tbnSize18.setActionCommand("18(M)");
        tbnSize20.setActionCommand("20(L)");
        //因為按鈕不顯示字串,故必須設定動作命令字串, 以便於回應事件時判別

        tbnSize16.setText(null); //設定JToggleButton按鈕不顯示字串
        tbnSize18.setText(null);
        tbnSize20.setText(null);

        tbnSize16.setSelected(true);//設定選取代表16字級的JToggleButton按鈕

        ButtonGroup bgToolBar = new ButtonGroup(); //宣告按鈕群組
        bgToolBar.add(tbnSize16); //將JToggleButton按鈕加入按鈕群組
        bgToolBar.add(tbnSize18);
        bgToolBar.add(tbnSize20);

        JPanel plStatus = new JPanel(new GridLayout(1, 1)); //宣告做為狀態列的JPanel
        lbStatus = new JLabel("游標位置 : 第 0 個字元"); //宣告顯示訊息的標籤
        plStatus.add(lbStatus);	//將標籤加入JPanel容器		

        Container cp = getContentPane(); //取得內容面版
        cp.add(tbFontSize, BorderLayout.NORTH); //將工具列加入內容面版
        cp.add(dpPane); //將虛擬桌面加入內容面版
        cp.add(plStatus, BorderLayout.SOUTH); //將狀態列加入內容面版

        addWindowListener(wa); //註冊回應WindowEvent事件的監聽器

        //設定視窗預設的關閉動作、視窗大小, 並顯示視窗
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 600);
        setVisible(true);
    }

    //傳出用於容納內部框架的虛擬桌面
    public JDesktopPane getDesktopPane() {
        return dpPane;
    }

    //建立文字編輯內部框架
    private void createInternalFrame(String... strArgs) {

        //依照是否傳入參數決定呼叫的TextInternalFrame類別建構子
        if (strArgs.length == 0) {
            tifCurrent = new TextInternalFrame();
        } else {
            tifCurrent = new TextInternalFrame(strArgs[0], strArgs[1]);
        }

        tifCurrent.addCaretListener(cl);
        //註冊回應游標CaretEvent事件的監聽器

        tifCurrent.addInternalFrameListener(ifl);
        //註冊回應InternalFrameEvent事件的監聽器

        JCheckBoxMenuItem cbmiWindow = tifCurrent.getMenuItem();
        //取得代表完成建立之TextInternalFrame物件的核取方塊選項

        wmWindow.add(cbmiWindow, tifCurrent);
        //將核取方塊選項與對應的TextInternalFrame物件新增至視窗功能表

        dpPane.add(tifCurrent);
        //將完成建立的TextInternalFrame物件加入虛擬桌面

        int FrameCount = dpPane.getAllFrames().length;
        //取得虛擬桌面內TextInternalFrame物件的個數

        tifCurrent.setLocation(20 * (FrameCount - 1), 20 * (FrameCount - 1));
        //設定TextInternalFrame物件所顯示文字編輯視窗框架左上角在虛擬桌面的座標

        try {
            tifCurrent.setSelected(true);
            //設定選取完成建立的TextInternalFrame物件
        } catch (java.beans.PropertyVetoException pve) {
            System.out.println(pve.toString());
        }
    }

    //定義並宣告回應InternalFrame事件的監聽器
    InternalFrameAdapter ifl = new InternalFrameAdapter() {

        //當內部框架取得游標焦點觸發事件將由此方法回應
        @Override
        public void internalFrameActivated(InternalFrameEvent e) {

            tifCurrent = (TextInternalFrame) e.getInternalFrame();
            //取得觸發InternalFrame事件的TextInternalFrame物件

            tifCurrent.getMenuItem().setSelected(true);
            //設定視窗功能表內代表此TextInternalFrame物件的核取方塊選項為選取

            //取得TextInternalFrame物件顯示內容使用的字級大小
            switch (tifCurrent.getFontSize()) {
                case 16:
                    cbmiSize16.setSelected(true); //設定對應的控制項為選取
                    tbnSize16.setSelected(true);
                    break;
                case 18:
                    cbmiSize18.setSelected(true);
                    tbnSize18.setSelected(true);
                    break;
                case 20:
                    cbmiSize20.setSelected(true);
                    tbnSize20.setSelected(true);
                    break;
            }

        }

        //當內部框架正在關閉時所觸發事件將由此方法回應
        @Override
        public void internalFrameClosing(InternalFrameEvent e) {
            wmWindow.remove(tifCurrent.getMenuItem());
            //移除視窗功能表內代表目前執行編輯之TextInternalFrame物件的選項
        }
    };

    //定義並宣告回應CaretEvent事件的監聽器
    CaretListener cl = new CaretListener() {

        //移動游標位置時, 將由此方法回應
        @Override
        public void caretUpdate(CaretEvent e) {

            if (e.getDot() != e.getMark()) {
                lbStatus.setText("目前位置 : 第 " + e.getDot()
                        + " 個字元" + ", 選取範圍 : " + e.getDot() + "至" + e.getMark());
                //設定狀態列內的文字

                acCut.setEnabled(true);
                acCopy.setEnabled(true);
                //設定執行剪下與複製動字的Action元件為有效
            } else {
                lbStatus.setText("目前位置 : 第 " + e.getDot() + " 個字元");
                //設定狀態列內的文字

                acCut.setEnabled(false);
                acCopy.setEnabled(false);
                //設定執行剪下與複製動字的Action元件為無效
            }
        }
    };

    //運用Action物件的名稱, 取得文字編輯面版提供的Action物件
    private Action getActionByName(String name) {

        Action[] actionsArray = tifCurrent.getTextPane().getActions();
        //取得文字編輯面版提供的Action物件

        for (Action elm : actionsArray) {
            //運用比對名稱的方式, 取得Action物件的
            if (elm.getValue(Action.NAME).equals(name)) {
                return elm;
            }
        }
        return null;
    }

    //定義執行文字字級設定的Action物件
    class FontSizeAction extends AbstractAction {

        public FontSizeAction(String text, ImageIcon icon,
                String desc, Integer mnemonic) {
            super(text, icon); //呼叫基礎類別建構子
            putValue(SHORT_DESCRIPTION, desc); //設定提示字串
            putValue(MNEMONIC_KEY, mnemonic); //設定記憶鍵
        }

        @Override
        public void actionPerformed(ActionEvent e) { //回應事件的執行動作
            //依照動作命令字串判別欲執行的動作
            switch (e.getActionCommand()) {
                case "20(L)":
                    tifCurrent.setFontSize(20);
                    //設定文字編輯面版使用20級字
                    cbmiSize20.setSelected(true); //設定對應的控制項為選取
                    tbnSize20.setSelected(true);
                    break;
                case "18(M)":
                    tifCurrent.setFontSize(18);
                    cbmiSize18.setSelected(true);
                    tbnSize18.setSelected(true);
                    break;
                default:
                    //預設16級字
                    tifCurrent.setFontSize(16);
                    cbmiSize16.setSelected(true);
                    tbnSize16.setSelected(true);
                    break;
            }
        }
    }

    //定義並宣告回應檔案功能表內選項被選取所觸發事件的監聽器
    ActionListener alFile = (ActionEvent e) -> {
        int result;
        try {
            //執行檔案開啟動作
            switch (e.getActionCommand()) {
                case "開啟舊檔(O)": {
                    JFileChooser fcOpen = new JFileChooser(
                            tifCurrent.getFilePath());
                    //宣告JFileChooser物件
                    fcOpen.addChoosableFileFilter(new TxtFileFilter("txt"));
                    //設定篩選檔案的類型
                    fcOpen.setDialogTitle("開啟舊檔"); //設定檔案選擇對話盒的標題
                    result = fcOpen.showOpenDialog(MDIEditor.this);
                    //顯示開啟檔案對話盒
                    if (result == JFileChooser.APPROVE_OPTION) { //使用者按下 確認 按鈕
                        File file = fcOpen.getSelectedFile(); //取得選取的檔案
                        createInternalFrame(file.getPath(), file.getName());
                        //以取得的檔案建立TextInternalFrame物件
                    }
                    break;
                }
                case "新增(N)":
                    //新增文件
                    createInternalFrame(); //建立沒有內容的TextInternalFrame物件
                    break;
                case "儲存檔案(S)":
                    //執行儲存檔案動作
                    String strPath = tifCurrent.getFilePath();
                    //取得目前TextInternalFrame物件開啟檔案的路徑與名稱
                    if (!tifCurrent.isNew()) {
                        //判斷TextInternalFrame物件開啟的是否為新的檔案
                        FileWriter fw = new FileWriter(strPath);
                        //建立輸出檔案的FileWriter物件
                        tifCurrent.write(fw);
                    } else {
                        saveFile(strPath); //儲存檔案
                    }
                    break;
                case "另存新檔(A)":
                    saveFile(tifCurrent.getFilePath()); //儲存檔案
                    break;
                case "PDF轉檔(Y)": {
                    JFileChooser fcOpen = new JFileChooser(
                            tifCurrent.getFilePath());
                    //宣告JFileChooser物件
                    fcOpen.addChoosableFileFilter(new TxtFileFilter("pdf"));
                    //設定篩選檔案的類型
                    fcOpen.setDialogTitle("選擇要轉檔的PDF"); //設定檔案選擇對話盒的標題
                    result = fcOpen.showOpenDialog(MDIEditor.this);
                    //顯示開啟檔案對話盒
                    if (result == JFileChooser.APPROVE_OPTION) { //使用者按下 確認 按鈕
                        File file = fcOpen.getSelectedFile(); //取得選取的檔案
                        System.setProperty("apple.awt.UIElement", "true");
                        ExtractText extractor = new ExtractText();
                        String fi[] = {file.getPath()};
                        String fe, ff;
                        extractor.startExtraction(fi);
                        fe = fi[0].substring(0, fi[0].length() - 3) + "txt";
                        ff = file.getName().substring(0, file.getName().length() - 3) + "txt";
                        createInternalFrame(fe, ff);
                        //以取得的檔案建立TextInternalFrame物件
                    }
                    break;
                }
                case "結束(E)":
                    MDIEditor.this.processWindowEvent(
                            new WindowEvent(MDIEditor.this,
                                    WindowEvent.WINDOW_CLOSING));
                    //執行WindowEvent事件, 觸發MDIEditor視窗框架的關閉視窗事件
                    break;
            }
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        } catch (BadLocationException ble) {
            System.err.println("位置不正確");
        }
    };

    ActionListener about = (ActionEvent e) -> {
        try {
            String url = "";
            switch (e.getActionCommand()) {
                case "00181034 李映澤":
                    url = "https://github.com/NatLee";
                    break;
                case "00257122 張語航":
                    url = "https://github.com/changyuhang";
                    break;
                case "00257138 吳彥澄":
                    url = "https://github.com/FinianWu";
                    break;
                case "00257141 陳平揚":
                    url = "https://github.com/ethanhunt0707";
                    break;
                case "00257148 陳威任":
                    url = "https://github.com/vic4113110631";
                    break;
            }
            Runtime.getRuntime().exec("cmd /c start " + url);
        } catch (IOException ioe) {
            System.err.println(ioe.toString());
        }
    };

    private void saveFile(String strPath) //儲存檔案
            throws IOException, BadLocationException {

        int pos = strPath.lastIndexOf("\\");

        String path = strPath.substring(0, pos + 1);
        String name = strPath.substring(pos + 1, strPath.length());
        JFileChooser fcSave = new JFileChooser(path);  //建立檔案選取對話盒
        fcSave.setSelectedFile(new File(name)); //設定選取的檔案

        fcSave.addChoosableFileFilter(new TxtFileFilter("txt"));
        //設定篩選檔案的類型

        fcSave.setDialogTitle("另存新檔"); //設定對話盒標題

        int result = fcSave.showSaveDialog(MDIEditor.this);
        //顯示檔案儲存對話盒

        if (result == JFileChooser.APPROVE_OPTION) {
            //使用者按下 確認 按鈕

            File file = fcSave.getSelectedFile(); //取得選取的檔案
            tifCurrent.write(new FileWriter(file));
            //將文字編輯內部框架的內容輸出至FileWriter物件

            tifCurrent.setFileName(file.getName()); //設定編輯檔案名稱
            tifCurrent.setFilePath(file.getPath()); //設定編輯檔案路徑
        }
    }

    //建立過濾檔案選擇對話盒內檔案類型的物件
    class TxtFileFilter extends FileFilter {

        String extension;

        public TxtFileFilter(String ext) { //建構子
            extension = ext;
        }

        @Override
        public boolean accept(File f) {
            if (f.isDirectory()) //若為資料夾傳回true
            {
                return true;
            }

            String ext = null;
            String s = f.getName(); //取得檔案名稱
            int i = s.lastIndexOf('.'); //尋找檔案名稱內的"."號

            if (i > 0 && i < s.length() - 1) {
                ext = s.substring(i + 1).toLowerCase();
                //從檔案名稱內取得副檔名字

                //判斷副檔名是否與檔案篩選物件的extension字串相同
                if (ext.equals(extension)) {
                    return true;
                }
            }

            return false;
        }

        //傳回檔案篩選物件欲篩選檔案類型的描述字串
        @Override
        public String getDescription() {
            return extension.equals("txt") ? "Text File" : "PDF File";
        }
    }

    //定義並宣告回應WindowEvent事件的WindowAdapter類別,
    //在關閉應用程式前, 運用監聽器判別程式內開啟的檔案是否已經儲存
    WindowAdapter wa = new WindowAdapter() {

        //回應視窗關閉事件的方法
        @Override
        public void windowClosing(WindowEvent e) {

            JInternalFrame[] ifAll = getDesktopPane().getAllFrames();
            //取得目前虛擬桌面內所有開啟的TextInternalFrame物件

            TextInternalFrame tifCurrent
                    = (TextInternalFrame) getDesktopPane().getSelectedFrame();
            //取得虛擬桌面目前選取的TextInternalFrame物件

            //判斷開啟的TextInternalFrame物件是否為0
            if (ifAll.length != 0) {

                //運用加強型for迴圈取得虛擬桌面內所有TextInternalFrame物件
                for (JInternalFrame elm : ifAll) {
                    try {
                        if (!((TextInternalFrame) elm).isChanged()) {
                            elm.setClosed(true); //關閉內部框架
                        } else {
                            int result
                                    = JOptionPane.showConfirmDialog(
                                            MDIEditor.this, "是否儲存?",
                                            "訊息", JOptionPane.YES_NO_CANCEL_OPTION,
                                            JOptionPane.INFORMATION_MESSAGE);
                            //顯示確認方塊

                            if (result == JOptionPane.NO_OPTION) //判斷是否按下 否 按鈕
                            {
                                elm.setClosed(true);
                            } else if (result == JOptionPane.CANCEL_OPTION) //判斷是否按下 取消 按鈕
                            {
                                return;
                            } else if (result == JOptionPane.YES_OPTION) {  //判斷是否按下 是 按鈕
                                String strPath = ((TextInternalFrame) elm).getFilePath();
                                //取得TextInternalFrame目前編輯檔案的路徑
                                //判斷TextInternalFrame目前編輯檔案是否為新的
                                if (!tifCurrent.isNew()) {
                                    tifCurrent.write(new FileWriter(strPath));
                                    //將TextInternalFrame的內容寫入FileWriter物件
                                } else {
                                    saveFile(strPath); //儲存檔案
                                }
                                elm.setClosed(true); //關閉內部框架
                            }
                        }
                    } catch (java.beans.PropertyVetoException pve) {
                        System.out.println(pve.toString());
                    } catch (IOException ioe) {
                        System.err.println(ioe.toString());
                    } catch (BadLocationException ble) {
                        System.err.println("位置不正確");
                    }
                }
            }

            ifAll = getDesktopPane().getAllFrames();
            //取得虛擬桌面目前開啟的所有內部框架

            if (ifAll.length == 0) //判斷內部框架的數目
            {
                System.exit(0); //結束應用程式
            }
        }
    };

    public static void main(String args[]) {
        MDIEditor api = new MDIEditor("PDF轉TXT文字閱讀編輯器"); //建立視窗框架	
    }
}
