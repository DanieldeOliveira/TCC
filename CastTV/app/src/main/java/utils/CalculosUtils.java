package utils;

import org.json.JSONException;
import org.json.JSONObject;

import activitys.CastApplication;

/**
 * Created by admin on 13/06/2016.
 */
public class CalculosUtils {


    private JSONObject dimensoes;

    public JSONObject calcularPosicao(int xSender, int ySender,CastApplication castApplication) throws JSONException {

        castApplication = CastApplication.getInstance();
        dimensoes = new JSONObject();
        int xEnviar = (int) ((xSender * castApplication.getWidthTV())/castApplication.getWidthTelaSmartphone());
        int yEnviar = (int) ((ySender * castApplication.getHeightTV())/castApplication.getHeightTelaSmartphone());

        dimensoes.put("x",xEnviar);
        dimensoes.put("y",yEnviar);

        return dimensoes;
    }
}
