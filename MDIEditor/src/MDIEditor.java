
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.filechooser.FileFilter;
import java.io.*;
import java.awt.*;
import java.awt.event.*; //引用處理事件的event套件

public class MDIEditor extends JFrame {

    JDesktopPane dpPane = new JDesktopPane(); //容納內部框架的虛擬桌面

    WindowMenu wmWindow = new WindowMenu("視窗(W)", KeyEvent.VK_W);
    //控制內部視窗畫面切換的功能表

    JToolBar toolBar;
    JLabel lbStatus; //顯示游標位置與選取字元的標籤

    InternalEditor internalEditor;
    FileMenu fileMenu;

    MDIEditor(String title) {
        super(title);//設定視窗名稱
        internalEditor = new InternalEditor(MDIEditor.this, wmWindow);
        internalEditor.createInternalFrame(); //建立第一個內部框架

        JMenu mnFile = new JMenu("檔案(F)"); //宣告檔案功能表
        mnFile.setMnemonic(KeyEvent.VK_F); //設定檔案功能表使用的記憶鍵
        fileMenu = new FileMenu(mnFile, MDIEditor.this);

        JMenu mnEdit = new JMenu("編輯(E)"); //宣告編輯功能表
        mnEdit.setMnemonic(KeyEvent.VK_E); //設定編輯功能表的記憶鍵
        Edit edit = new Edit(mnEdit, internalEditor);

        JMenu mnFontSize = new JMenu("字級(S)"); //宣告字級功能表
        mnFontSize.setMnemonic(KeyEvent.VK_S); //設定字級功能表的記憶鍵
        FontSize fontSize = new FontSize(mnFontSize, internalEditor);

        JMenu mnMusic = new JMenu("音樂(M)"); //宣告音樂
        mnMusic.setMnemonic(KeyEvent.VK_M); //設定檔案功能表使用的記憶鍵
        MusicMenu musicMenu = new MusicMenu(mnMusic, MDIEditor.this);

        JMenu mnAbout = new JMenu("關於(R)"); //宣告關於
        mnAbout.setMnemonic(KeyEvent.VK_R); //設定檔案功能表使用的記憶鍵
        AboutMenu aboutMenu = new AboutMenu(mnAbout);

        JMenuBar jmb = new JMenuBar(); //宣告功能表列物件
        setJMenuBar(jmb); //設定視窗框架使用的功能表列
        jmb.add(mnFile); //將功能表加入功能表列
        jmb.add(mnEdit);
        jmb.add(mnFontSize);
        jmb.add(wmWindow);
        jmb.add(mnMusic);
        jmb.add(mnAbout);

        toolBar = new JToolBar(); //新增工具列
        toolBar.add(internalEditor.tbnSize16); //將JToggleButton按鈕加入工具列
        toolBar.add(internalEditor.tbnSize18);
        toolBar.add(internalEditor.tbnSize20);

        JPanel plStatus = new JPanel(new GridLayout(1, 1)); //宣告做為狀態列的JPanel
        lbStatus = new JLabel("游標位置 : 第 0 個字元"); //宣告顯示訊息的標籤
        plStatus.add(lbStatus);	//將標籤加入JPanel容器		

        Container cp = getContentPane(); //取得內容面版
        cp.add(toolBar, BorderLayout.NORTH); //將工具列加入內容面版
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

    public void saveFile(String strPath) //儲存檔案
            throws IOException, BadLocationException {

        int pos = strPath.lastIndexOf("\\");

        String path = strPath.substring(0, pos + 1);
        String name = strPath.substring(pos + 1, strPath.length());
        JFileChooser fcSave = new JFileChooser(path);  //建立檔案選取對話盒
        fcSave.setSelectedFile(new File(name)); //設定選取的檔案

        FileFilter fileFilter = fileMenu.NewFileFilter("TXT File", new String[]{"txt"});
        fcSave.addChoosableFileFilter(fileFilter);
        //設定篩選檔案的類型

        fcSave.setDialogTitle("另存新檔"); //設定對話盒標題

        int result = fcSave.showSaveDialog(MDIEditor.this);
        //顯示檔案儲存對話盒

        if (result == JFileChooser.APPROVE_OPTION) {
            //使用者按下 確認 按鈕
            File file = fcSave.getSelectedFile(); //取得選取的檔案
            internalEditor.tifCurrent.write(new FileWriter(file));
            //將文字編輯內部框架的內容輸出至FileWriter物件
            internalEditor.tifCurrent.setFileName(file.getName()); //設定編輯檔案名稱
            internalEditor.tifCurrent.setFilePath(file.getPath()); //設定編輯檔案路徑
        }
    }

    //在關閉應用程式前, 運用監聽器判別程式內開啟的檔案是否已經儲存
    WindowAdapter wa = new WindowAdapter() {

        //回應視窗關閉事件的方法
        @Override
        public void windowClosing(WindowEvent e) {

            JInternalFrame[] ifAll = getDesktopPane().getAllFrames();
            //取得目前虛擬桌面內所有開啟的TextInternalFrame物件

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
                                if (!internalEditor.tifCurrent.isNew()) {
                                    internalEditor.tifCurrent.write(new FileWriter(strPath));
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

    public void Exit() {//離開程式
        processWindowEvent(new WindowEvent(MDIEditor.this, WindowEvent.WINDOW_CLOSING));
        //執行WindowEvent事件, 觸發MDIEditor視窗框架的關閉視窗事件
    }

}
