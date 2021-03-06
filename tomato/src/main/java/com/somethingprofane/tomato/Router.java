package com.somethingprofane.tomato;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by somethingPr0fane on 2/25/14.
 */
public class Router implements Parcelable {
    /**
     * This class with hold all the informaiton about the router:
     * IP address
     * MAC Address
     * Devices Connected
     * etc.
     */

    String routerName;
    String wanHwAddr;
    String lanIpAddr;
    String modelName;
    String uptime;
    String totalRam;
    String usrname;
    String url;
    String pswrd;
    String httpId;
    String freeRam;
    ArrayList<Device> deviceList = new ArrayList<Device>();

    String ssid;
    String subnet;
    String dhcpPool1;
    String dhcpPool2;
    String sharedKey;
    String encryption;
    String security;
    String dhcpLeaseTime;

    /**
     * Create a new Router object
     */
    public Router (){

        this.url = "http://" + TomatoMobile.getInstance().getIpaddress();
        this.usrname = TomatoMobile.getInstance().getUsername();
        this.pswrd = TomatoMobile.getInstance().getPassword();
        Connection conn = new Connection();
        String returnedHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = conn.PostToWebadress(url+"/status-data.jsx", tempHashMap);
            //Set all the values with the returned HTML
            setRouterName(returnedHtml);
            setFreeRam(returnedHtml);
            setLanIpAddr(returnedHtml);
            setModelName(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setWanHwAddr(returnedHtml);
            setSsid(returnedHtml);
            setSubnet(returnedHtml);
            setDhcpPool1(returnedHtml);
            setDhcpPool2(returnedHtml);
            setSharedKey(returnedHtml);
            setEncryption(returnedHtml);
            setDhcpLeaseTime(returnedHtml);
            setSecurity(returnedHtml);
            
            setDeviceList();
            System.out.println("Returned Html " + returnedHtml);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getHttpId() {
        return httpId;
    }

    public void setHttpId(String httpId) {
        this.httpId = httpId;
    }

    public String getUrl() {
        return url;
    }

    public String getPswrd() {
        return pswrd;
    }

    public String getUsrname(){
        return usrname;
    }

    public String getRouterName() {
        return routerName;
    }

    /**
     *
     * @param html string of html regex is run on to extract routerName
     */
    public void setRouterName(String html) {

       routerName = new Parser().parserRouterName(html);

    }

    public String getWanHwAddr() {
        return wanHwAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract wanAddress
     */
    public void setWanHwAddr(String html) {

     wanHwAddr = new Parser().parseWanHwAddr(html);

    }

    public String getLanIpAddr() {
        return lanIpAddr;
    }

    /**
     *
     * @param html string of html regex is run on to extract lan ip address
     */
    public void setLanIpAddr(String html) {

        lanIpAddr = new Parser().parseLanIpAddr(html);

    }


    public String getModelName() {
        return modelName;
    }

    /**
     *
     * @param html - string of html regex is run on to extract modelName
     */
    public void setModelName(String html) {

        modelName = new Parser().parseModelName(html);

    }

    public String getUptime() {
        return uptime;
    }

    /**
     *
     * @param html string of html regex is run on to extract setuptime
     */
    public void setUptime(String html) {

        uptime = new Parser().parseUptime(html);

    }

    public String getTotalRam() {
        return totalRam;
    }

    /**
     *
     * @param html - string of html regex is run on to extract totalRam
     */
    public void setTotalRam(String html) {

        totalRam = new Parser().parseTotalRam(html);

    }

    public String getFreeRam() {
        return freeRam;
    }

    public void setFreeRam(String html) {

        freeRam = new Parser().parseFreeRam(html);
    }

// Adding in the Basic-Network getters, setters
    public String getSsid() {
        return ssid;
    }
    public void setSsid(String html) {ssid = new Parser().parseSsid(html);}

    public String getSubnet() {return subnet;}
    public void setSubnet(String html) {subnet = new Parser().parseSubnet(html);}

    public String getDhcpPool1() {
        return dhcpPool1;
    }
    public void setDhcpPool1(String html) {dhcpPool1 = new Parser().parseDhcpPool1(html);}

    public String getDhcpPool2() {
        return dhcpPool2;
    }
    public void setDhcpPool2(String html) {dhcpPool2 = new Parser().parseDhcpPool2(html);}

    public String getSharedKey() {
        return sharedKey;
    }
    public void setSharedKey(String html) { sharedKey = new Parser().parseSharedKey(html);}

    public String getEncryption() {
        return encryption;
    }
    public void setEncryption(String html) {encryption = new Parser().parseEncryption(html);}

    public String getSecurity() {
        return security;
    }
    public void setSecurity(String html) {security = new Parser().parseSecurity(html);}

    public String getDhcpLeaseTime() {
        return dhcpLeaseTime;
    }
    public void setDhcpLeaseTime(String html) {dhcpLeaseTime = new Parser().parseDhcpLeaseTime(html);}
// End Basic-Network


    public ArrayList<Device> getDeviceList() {
        return deviceList;
    }

    public void setDeviceList() {
        // Get the html from the status-devices page
        Connection conn = new Connection();
        String deviceHTML;
        deviceHTML = conn.GetHTMLFromURL("http://" + TomatoMobile.getInstance().getIpaddress() + "/status-devices.asp");
        deviceList = new Parser().parseDeviceList(deviceHTML);
    }

    public ArrayList getDeviceListNames() {
        ArrayList<String> deviceListName = new ArrayList<String>();

        for (int x=0; x<this.getDeviceList().size(); x++){
            deviceListName.add(this.getDeviceList().get(x).getDeviceName());
        }
        return deviceListName;

    }

    public ArrayList getDeviceListIPs(){

        ArrayList<String> deviceListIp = new ArrayList<String>();

        for (int x=0; x<this.getDeviceList().size(); x++){
            deviceListIp.add( "IP: "+this.getDeviceList().get(x).getDeviceIPAddr());
        }
        return deviceListIp;
    }

    /**
     * Used to refresh router information - namely FreeRam, TotalRam, Uptime, and DeviceList
     */
    public void refresh(){
        Connection conn = new Connection();
        String returnedHtml = null;
        setHttpId(conn.GetRouterHTTPId());
        HashMap <String, String> tempHashMap = conn.buildParamsMap("_http_id", getHttpId());

        try {
            returnedHtml = conn.PostToWebadress(url+"/status-data.jsx", tempHashMap);
            //Set all the values with the returned HTML
            setRouterName(returnedHtml);
            setFreeRam(returnedHtml);
            setTotalRam(returnedHtml);
            setUptime(returnedHtml);
            setSsid(returnedHtml);
            setSecurity(returnedHtml);
            setSharedKey(returnedHtml);
            setDhcpPool1(returnedHtml);
            setDhcpPool2(returnedHtml);
            setEncryption(returnedHtml);

            setDeviceList();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final Parcelable.Creator<Router> CREATOR
            = new Parcelable.Creator<Router>() {
        public Router createFromParcel(Parcel in) {
            return new Router(in);
        }

        public Router[] newArray(int size) {
            return new Router[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int i) {
        out.writeString(routerName);
        out.writeString(wanHwAddr);
        out.writeString(lanIpAddr);
        out.writeString(modelName);
        out.writeString(uptime);
        out.writeString(totalRam);
        out.writeString(usrname);
        out.writeString(url);
        out.writeString(pswrd);
        out.writeString(httpId);
        out.writeString(ssid);
        out.writeString(subnet);
        out.writeString(sharedKey);
        out.writeString(security);
        out.writeString(encryption);
        out.writeString(dhcpPool1);
        out.writeString(dhcpPool2);
        out.writeString(dhcpLeaseTime);
        out.writeList(deviceList);

    }

    private Router (Parcel in){
        deviceList = new ArrayList<Device>();
        routerName = in.readString();
        wanHwAddr = in.readString();
        lanIpAddr = in.readString();
        modelName = in.readString();
        uptime = in.readString();
        totalRam = in.readString();
        usrname = in.readString();
        url = in.readString();
        pswrd = in.readString();
        httpId = in.readString();
        ssid = in.readString();
        subnet = in.readString();
        sharedKey = in.readString();
        security = in.readString();
        dhcpLeaseTime = in.readString();
        dhcpPool1 = in.readString();
        dhcpPool2 = in.readString();
        encryption = in.readString();
        in.readList(deviceList,getClass().getClassLoader());
    }


}