package de.sqrls.ttn.gwsc.net.api;

/**
 * Created by zero on 01.11.15.
 */
public class ParserFactory {


    public static IGatewayParser createGatewayParser(final String apiVersion){
        //likely you could do something clever with classloaders here, but this is enough for now..
        if(ApiConstants.API_V0.equals(apiVersion)){
            return new de.sqrls.ttn.gwsc.net.api.v0.GatewayParser();
        }

        throw new IllegalArgumentException("invalid api identifier >"+apiVersion+"<");
    }

}
