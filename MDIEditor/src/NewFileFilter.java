
import java.io.File;
import javax.swing.filechooser.FileFilter;

    //建立過濾檔案選擇對話盒內檔案類型的物件
class NewFileFilter extends FileFilter {

    final String desc;
    final String[] allowed_extensions;

    public NewFileFilter(String desc, String[] allowed_extensions) {
        this.desc = desc;
        this.allowed_extensions = allowed_extensions;
    }

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
}
