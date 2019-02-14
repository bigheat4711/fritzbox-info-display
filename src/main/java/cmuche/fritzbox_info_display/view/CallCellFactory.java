package cmuche.fritzbox_info_display.view;

import cmuche.fritzbox_info_display.model.Call;
import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class CallCellFactory implements Callback<ListView<Object>, ListCell<Object>>
{

  private final URL fxmlSource;

  public CallCellFactory(@NamedArg("fxmlSource") String fxmlSource) throws MalformedURLException
  {
    this.fxmlSource = new URL(fxmlSource);
  }

  @Override
  public ListCell<Object> call(ListView<Object> lv)
  {
    return new ListCell<Object>()
    {
      @Override
      protected void updateItem(Object item, boolean empty)
      {
        super.updateItem(item, empty);
        if (item == null || empty)
        {
          setGraphic(null);
        } else
        {
          try
          {
            FXMLLoader loader = new FXMLLoader(fxmlSource);
            Node node = loader.load();
            loader.getNamespace().put("item", item);
            CallCellController contr = loader.getController();
            contr.setCall((Call) item);
            setGraphic(node);
          }
          catch (IOException e)
          {
            e.printStackTrace();
            setGraphic(null);
          }
        }
      }
    };
  }

}