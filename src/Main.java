
import entrepreneurship.Entrepreneurship;
import presentation.Controller;
import presentation.UI;
import science.ScienceChannel;
import sugar.SugarChannel;
import terror.TerrorChannel;

public class Main {
    public static void main(String[] args) {
        DatasetReader datasetReader = new DatasetReader();

        UI ui = new UI();
        //Canviar el fitxer del canal emprenedoria aquí!
        Entrepreneurship entrepreneurship = new Entrepreneurship(
                datasetReader.buildGraph("files/CityXXS.paed")
        );

        //Canviar el fitxer del canal sucre aquí!
        SugarChannel sugarChannel = datasetReader.buildTree("files/treeXXS.paed");

        //Canviar el fitxer del canal terror aquí!
        TerrorChannel terrorChannel = datasetReader.buildRTree("files/rtreeXXS.paed");

        //Canviar fitxer del canal ciència aquí!
        ScienceChannel scienceChannel = datasetReader.buildTable("files/tablesXXS.paed");

        Controller controller = new Controller(ui, entrepreneurship, sugarChannel, terrorChannel, scienceChannel);
        controller.run();
    }
}
