package edu.skku.cs.pa3.contract;

import org.json.JSONObject;

public interface ContractLogin {
    interface view {
        void success(String result,int TypeNum);
    }

    interface presenter {
        void connection(JSONObject jData,int typeNum);
    }
}
