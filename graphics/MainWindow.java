�PNG

   IHDR   d   d   p�T   bKGD � � �����  IDATx���O�MQ ����'CR�RJ�)R�(�I���,ǎ���%K۱SV��ŎD�("�$�����y����{�x�O��{���{���w޽�$I�$I�$I�u"u -�����AT��p
K��0�d���H�(nd	�W�,�,a�V#]��� ؕ.�"�X~��6�� �7���\�G��D��){�l���F.�/�"0�Aβ��F��G�W���8ToLX\�"��5�[�A�2��F/��#��5p����j5ŧ�ߑmpؐA�A��F���O#��-0]ML�dp��> ׀͉s�T!���}�#0Cy��ң	�q��}���[H���#�����k�9�@��4|��7 w��5崐&[���}���[Ȑƀ����m�!����&�j�vC�++�i!]L�"�����.�[H�SH�J�ӸnMI9-�G����C��#�XHź��R<��i!: <����xc=��$p��}$V���e��p��S�}��XH"�)�ɷڷ�D��{XHV&�3����m�-,$+��i�����~�Î�����%+��'��̏���/��ܡ���b���~o�*�?P��"�?�vPgpr�A]�=�GU�@�>U�CITEp�B��=�e�����S�J6LpO��� �=-�B�w �ܩ5j�Kp'��Q��Nϔ@��N`�Hkp��K�5��`&{����Y�2�H9�Xx�O�5���'6ؿ��D�?tɊy$I�$I�$I���gH����~    IEND�B`�package graphics;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class MainWindow extends Application {
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("mainWindowFXML.fxml"));
        Scene scene = new Scene(root);

        stage.setScene(scene);

        stage.setTitle("Ford-Bellman algorithm");
        stage.setWidth(800);
        stage.setHeight(600);
        stage.show();
    }
}