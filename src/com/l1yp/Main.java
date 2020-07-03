package com.l1yp;

import com.l1yp.stream.ObjectReader;
import com.l1yp.util.Bits;
import com.l1yp.util.HexUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;

/**
 * @Author Lyp
 * @Date 2020-07-02
 * @Email l1yp@qq.com
 */
public class Main {
    public static void main(String[] args) throws Exception {

        // ByteArrayInputStream bis = new ByteArrayInputStream(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200055A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C00036D617074000F4C6A6176612F7574696C2F4D61703B4C000570686F6E6571007E00027870007070737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400066C79702D6B317400066C79702D76317400056C79702D6B7400056C79702D767870"));
        // ObjectInputStream is = new ObjectInputStream(bis);
        // Object object = is.readObject();
        // System.out.println("object = " + object);
        // System.exit(0);
        // ByteArrayOutputStream bos = new ByteArrayOutputStream();
        // ObjectOutputStream os = new ObjectOutputStream(bos);
        // UserSet userSet = new UserSet();
        // userSet.longVal = 10L;
        // userSet.intVal = 100;
        // os.writeObject(userSet);
        // System.out.println(HexUtil.bin2hex(bos.toByteArray()));
        // System.exit(0);
        // User user = new User();
        // user.id = 10086;
        // user.age = 24;
        // user.name = "Lyp";
        // user.money = 1048576;
        // user.accountName = "Zyl";
        // UserDetail detail = new UserDetail();
        // detail.contacts = new LinkedList<>();
        // Account account1 = new Account();
        // account1.money = 100;
        // account1.accountName = "name1";
        // Account account2 = new Account();
        // account2.money = 200;
        // account2.accountName = "name2";
        //
        // detail.contacts.add(account1);
        // detail.contacts.add(account2);
        // detail.email = "l1yp@qq.com";
        // detail.phone = "18877811997";
        // detail.sex = true;
        //
        // detail = new UserDetail();
        // detail.map = new LinkedHashMap<>();
        // detail.map.put("lyp-k", account1);
        // detail.map.put("lyp-k1", account2);
        //
        // os.writeObject(detail);
        // System.out.println(HexUtil.bin2hex(bos.toByteArray()));
        // System.exit(0);

        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720012636F6D2E6C3179702E4D61696E2455736572C2AF4264F2183B620200034900036167654C00076163636F756E747400174C636F6D2F6C3179702F4D61696E244163636F756E743B4C0008757365724E616D657400124C6A6176612F6C616E672F537472696E673B78700000001873720015636F6D2E6C3179702E4D61696E244163636F756E7456C5C3A3777B9BD90200024C00096163636F756E74496471007E00024C00056D6F6E65797400104C6A6176612F6C616E672F4C6F6E673B787074000836323230323132307372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000000000029A7400034C7970"));
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED00057372000D636F6D2E6C3179702E557365723A9F979F3795F7550200034900036167654900056D6F6E65794C0008757365724E616D657400124C6A6176612F6C616E672F537472696E673B78720010636F6D2E6C3179702E4163636F756E74A58C165651E6E6360200034900036D6D6D4A00056D6F6E65794C00096163636F756E74496471007E0001787000000000000000000000000070000000180000270F7400034C7970"));
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED00057372000D636F6D2E6C3179702E557365727ECD7C2251C23D6B0200034900036167654A000269644C00046E616D657400124C6A6176612F6C616E672F537472696E673B78720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0001787000000000001000007400035A796C0000001800000000000027667400034C7970"));
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED00057372000D636F6D2E6C3179702E557365721C6966270AACA5C60200044900036167654A000269644C000664657461696C7400154C636F6D2F6C3179702F5573657244657461696C3B4C00046E616D657400124C6A6176612F6C616E672F537472696E673B78720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0002787000000000001000007400035A796C00000018000000000000276673720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200045A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C71007E00024C000570686F6E6571007E0002787001737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A6578700000000377040000000374000B313838373738313139393774000B313830373738353531333774000B31333134353230353230307874000B6C3179704071712E636F6D71007E000B7400034C7970"));

        // ArrayList
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200045A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C000570686F6E6571007E0002787001737200136A6176612E7574696C2E41727261794C6973747881D21D99C7619D03000149000473697A6578700000000277040000000273720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0002787000000000000000647400056E616D65317371007E000600000000000000C87400056E616D65327874000B6C3179704071712E636F6D74000B3138383737383131393937"));
        // LinkedList
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200045A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C000570686F6E6571007E0002787001737200146A6176612E7574696C2E4C696E6B65644C6973740C29535D4A608822030000787077040000000273720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0002787000000000000000647400056E616D65317371007E000600000000000000C87400056E616D65327874000B6C3179704071712E636F6D74000B3138383737383131393937"));
        // hashmap str - str
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200055A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C00036D617074000F4C6A6176612F7574696C2F4D61703B4C000570686F6E6571007E00027870007070737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400066C79702D6B317400066C79702D76317400056C79702D6B7400056C79702D767870"));
        // hashmap str - account
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200055A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C00036D617074000F4C6A6176612F7574696C2F4D61703B4C000570686F6E6571007E00027870007070737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400066C79702D6B3173720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0002787000000000000000C87400056E616D65327400056C79702D6B7371007E000800000000000000647400056E616D65317870"));
        // linkedhashmap str - account
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720013636F6D2E6C3179702E5573657244657461696C8FA541B58A75F8310200055A00037365784C0008636F6E74616374737400104C6A6176612F7574696C2F4C6973743B4C0005656D61696C7400124C6A6176612F6C616E672F537472696E673B4C00036D617074000F4C6A6176612F7574696C2F4D61703B4C000570686F6E6571007E00027870007070737200176A6176612E7574696C2E4C696E6B6564486173684D617034C04E5C106CC0FB0200015A000B6163636573734F72646572787200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400056C79702D6B73720010636F6D2E6C3179702E4163636F756E742D00CC9B0944ACBD0200024A00056D6F6E65794C000B6163636F756E744E616D6571007E0002787000000000000000647400056E616D65317400066C79702D6B317371007E000900000000000000C87400056E616D6532780070"));
        // userSet
        // ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720010636F6D2E6C3179702E557365725365747736A545B16B7B1C0200014C00056974656D737400134C6A6176612F7574696C2F486173685365743B7870737200176A6176612E7574696C2E4C696E6B656448617368536574D86CD75A95DD2A1E020000787200116A6176612E7574696C2E48617368536574BA44859596B8B7340300007870770C000000103F400000000000027400036C79707400037A796C78"));

        ObjectReader reader = new ObjectReader(HexUtil.hex2bin("ACED000573720010636F6D2E6C3179702E5573657253657483F5BDBB803789AE0200024C0006696E7456616C7400134C6A6176612F6C616E672F496E74656765723B4C00076C6F6E6756616C7400104C6A6176612F6C616E672F4C6F6E673B7870737200116A6176612E6C616E672E496E746567657212E2A0A4F781873802000149000576616C7565787200106A6176612E6C616E672E4E756D62657286AC951D0B94E08B0200007870000000647372000E6A6176612E6C616E672E4C6F6E673B8BE490CC8F23DF0200014A000576616C75657871007E0005000000000000000A"));
        Object result = reader.readObject();
        System.out.println("object = " + result);


    }

}
