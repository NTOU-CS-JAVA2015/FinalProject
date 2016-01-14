import javax.swing.*;
import java.awt.event.*;
import java.util.*;

//以衍生JMenu類別實作ActionListener介面的方式, 
//定義控制內部框架之功能表的WindowMenu類別
class WindowMenu extends JMenu implements ActionListener {

    ButtonGroup bgWindow = new ButtonGroup(); //宣告按鈕群組

    HashMap<JCheckBoxMenuItem, TextInternalFrame> hmMenuItem
            = new HashMap<>();
    //宣告儲存核取方塊選項與對應內部框架的HashMap容器

    WindowMenu(String text, int mnemonic) { //建構子
        super(text); //呼叫基礎類別建構子
        setMnemonic(KeyEvent.VK_W); //設定記憶鍵		
    }

    //將代表內部框架的核取方塊選項與對應的內部框架新增至功能表
    public JMenuItem add(JCheckBoxMenuItem cbmi, TextInternalFrame tif) {
        cbmi.addActionListener(this); //註冊回應ActionEvent事件的監聽器
        cbmi.setState(true); //設定選取核取方塊選項
        hmMenuItem.put(cbmi, tif); //將核取方塊選項與內部框架新增至HashMap容器
        bgWindow.add(cbmi); //將核取方塊選項新增至按鈕群組
        return super.add(cbmi); //呼叫基礎類別的add()方法加入核取方塊選項
    }

    public void remove(JCheckBoxMenuItem cbmi) { //移除指定的核取方塊選項
        super.remove(cbmi); //呼叫基礎類別的remove()方法移除核取方塊選項
        cbmi.removeActionListener(this); //移除回應ActionEvent事件的監聽器
        hmMenuItem.remove(cbmi); //移除HashMap容器內儲存核取方塊選項的元素
        bgWindow.remove(cbmi); //從按鈕群組移除核取方塊選項
    }

    //回應ActionEvent事件的方法
    @Override
    public void actionPerformed(ActionEvent e) {

        JInternalFrame tifCurrent
                = hmMenuItem.get((JCheckBoxMenuItem) e.getSource());
        //運用核取方塊選項從HashMap容器取得對應的TextInternalFrame物件
        try {
            tifCurrent.setSelected(true); //設定取得的TextInternalFrame物件為選取
        } catch (java.beans.PropertyVetoException pve) {
            System.out.println(pve.toString());
        }
    }
}
