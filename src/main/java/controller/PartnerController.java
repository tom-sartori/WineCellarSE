package controller;

import com.mongodb.client.FindIterable;
import connectiontodb.Connection;
import model.Partner;
import org.bson.Document;

public class PartnerController {

    public PartnerController() {
    }

    public Partner getPartners(){
        return Connection.findFirst("partners");
    }
}
