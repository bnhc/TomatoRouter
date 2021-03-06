package com.somethingprofane.tomato;

import com.somethingprofane.db.DatabaseManager;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by somethingPr0fane on 2/6/14.
 */
public class Parser {

    public String parserRouterName(String html){
        String pattern = "router_name: '(.*?)'";
        String routerName = null;
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            routerName = m.group(1);
        }
        return routerName;
    }

    public String parseWanHwAddr(String html){
        String wanHwAddr = null;
        String pattern = "wan_hwaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            wanHwAddr = m.group(1);
        }
        return wanHwAddr;
    }

    public String parseLanIpAddr(String html){
        String lanIpAddr = null;
        String pattern = "lan_ipaddr: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            lanIpAddr = m.group(1);
        }
        return lanIpAddr;
    }

    public String parseModelName(String html){
        String modelName = null;
        String pattern = "t_model_name: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            modelName = m.group(1);
        }
        return modelName;
    }

    public String parseUptime(String html){
        String uptime = null;
        String pattern = "uptime_s: '(.*?)'";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            uptime = m.group(1);
        }
        return uptime;
    }

    public String parseTotalRam(String html){
        String totalRam = null;
        String pattern = "freeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            double totalRamNum = Integer.parseInt(m.group(1));
            totalRamNum = totalRamNum / 1000;
            totalRam = totalRamNum + "kb";
        }
        return totalRam;
    }

    public String parseFreeRam(String html){
        String freeRam = null;
        String pattern = "\\sfreeram: (.*?),";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            System.out.println(m.group(1));
            double freeRamNum = Integer.parseInt(m.group(1));
            freeRamNum = freeRamNum / 1000;
            freeRam = freeRamNum + "kb";
        }
        return freeRam;
    }

    public String parseSsid(String html){
        String ssid = null;
        String regex = "wl_ssid: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
//            System.out.println("SSID! " + m.group(1));
            ssid = m.group(1);
        }
        return ssid;
    }
    public String parseSubnet(String html){
        String subnet = null;
        String regex = "lan_netmask: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            subnet = m.group(1);
        }
        return subnet;
    }
    public String parseDhcpPool1(String html){
        String dPool1 = null;
        String regex = "dhcpd_startip: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            dPool1 = m.group(1);
        }
        return dPool1;
    }
    public String parseDhcpPool2(String html){
        String dPool2 = null;
        String regex = "dhcpd_endip: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            dPool2 = m.group(1);
        }
        return dPool2;
    }
    public String parseDhcpLeaseTime(String html){
        String dTime = null;
        String regex = "dhcp_lease: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            dTime = m.group(1);
        }
        return dTime;
    }
    public String parseSharedKey(String html){
        String sKey = null;
        String regex = "wl_wpa_psk: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            sKey = m.group(1);
        }
        return sKey;
    }
    public String parseEncryption(String html){
        String encrypt = null;
        String regex = "wl_crypto: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            encrypt = m.group(1);
        }
        return encrypt;
    }
    public String parseSecurity(String html){
        String security = null;
        String regex = "security_mode2: '(.*?)'";
        Pattern r = Pattern.compile(regex, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            security = m.group(1);
        }
        return security;
    }

    public String[] parseAccessRestrictionRules(String html){
        String[] accessRulesArray;
        accessRulesArray = new String[0];
        String pattern = "rrules = \\[(.*?)\\]";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(html);
        if(m.find()){
            accessRulesArray = m.group(1).trim().replace("'", "").split(",");
        }
        return accessRulesArray;
    }

    public ArrayList<Device> parseDeviceList(String deviceHTML){
        ArrayList<Device> deviceListDHCP;
        ArrayList<Device> deviceListWIFI;
        deviceListDHCP = parseDeviceDHCPLeaseInfo(deviceHTML);
        deviceListWIFI = parseWIFIConnectivityInfo(deviceHTML);
        deviceListDHCP = compareWiredWirelessDevices(deviceListDHCP, deviceListWIFI);
        return deviceListDHCP;
    }

    private ArrayList<Device> compareWiredWirelessDevices(ArrayList<Device> deviceListDHCP, ArrayList<Device> deviceListWIFI) {

        for(Device device : deviceListDHCP){
            boolean toUpdate = true;

            if(deviceListWIFI != null) {
                for (Device deviceWifi : deviceListWIFI) {
                    if (deviceWifi.getDeviceMacAddr().equals(device.getDeviceMacAddr())) {
                        device.setDeviceType("wireless");
                        device.setDeviceWifiConnected(true);
                        DatabaseManager.getInstance().updateDevice(device);
                        toUpdate = false;
                        break; // Break out of first for loop
                    }
                }
            }

            if(toUpdate) {
                // Is the Device contained within the DB already?
                checkSetDeviceType(device);
                DatabaseManager.getInstance().updateDevice(device);
            }
        }
        return deviceListDHCP;
    }

    private void checkSetDeviceType(Device device) {
        Device dbDevice;
        if(DatabaseManager.getInstance() != null){
            dbDevice = DatabaseManager.getInstance().getDeviceById(device.getDeviceMacAddr());
            if(dbDevice != null) {
                //Check to see if device type is already set:
                if(dbDevice.getDeviceType().equals("wireless")){
                    device.setDeviceWifiConnected(false);
                }
            } else {
                device.setDeviceType("wired");
                device.setDeviceWifiConnected(false);
            }
        }
    }

    private ArrayList<Device> parseDeviceDHCPLeaseInfo(String deviceHTML) {
        String[] deviceInfoArray;
        ArrayList<Device> deviceList = new ArrayList<Device>();
        String pattern = "dhcpd_lease([^;]*)";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(deviceHTML);
        if(m.find()){
            pattern = "(?<=\\[)(.*?)(?=\\])";
            Pattern r2 = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m2 = r2.matcher(m.group(1));
            while(m2.find()){
                deviceInfoArray = m2.group(1).trim().replaceAll("[\\[']", "").split(",");
                Device device = new Device();
                device.setDeviceName(deviceInfoArray[0]);
                device.setDeviceIPAddr(deviceInfoArray[1]);
                device.setDeviceMacAddr(deviceInfoArray[2]);
                device.setDeviceConnTime(deviceInfoArray[3] + deviceInfoArray[4]);
                //TODO check the database and see if the device is in the db to set the device type.
                verifyDeviceToDB(device);
                deviceList.add(device);
            }
        }
        return deviceList;
    }

    private void verifyDeviceToDB(Device device) {
        Device device2;
        if(DatabaseManager.getInstance() != null) {
            device2 = DatabaseManager.getInstance().getDeviceById(device.getDeviceMacAddr());
            if(device2 == null){
                // Do nothing.
                device.setDeviceRestricted(false);
                DatabaseManager.getInstance().addDevice(device);
            } else if(device2.getDeviceMacAddr().equals(device.getDeviceMacAddr())){
                device.setDeviceRestricted(device2.isDeviceRestricted());
                device.setDeviceType(device2.getDeviceType());
                device.setDeviceGroup(device2.getDeviceGroup());
            }
        }
    }

    private ArrayList<Device> parseWIFIConnectivityInfo(String deviceHTML) {
        String[] deviceInfoArray;
        ArrayList<Device> deviceList = new ArrayList<Device>();
        String pattern = "wldev([^;]*)";
        Pattern r = Pattern.compile(pattern, Pattern.DOTALL);
        Matcher m = r.matcher(deviceHTML);
        if(m.find()){
            pattern = "(?<=\\[)(.*?)(?=\\])";
            Pattern r2 = Pattern.compile(pattern, Pattern.DOTALL);
            Matcher m2 = r2.matcher(m.group(1));
            while(m2.find()){
                deviceInfoArray = m2.group(1).trim().replaceAll("[\\[']", "").split(",");
                if(deviceInfoArray[0].equals("")){
                    return null;
                }
                Device device = new Device();
                device.setDeviceMacAddr(deviceInfoArray[1]);
                deviceList.add(device);
            }
        }
        return deviceList;
    }
}
