package com.github.DNAProject.smartcontract;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.DNAProject.DnaSdk;
import com.github.DNAProject.DnaSdkTest;
import com.github.DNAProject.account.Account;
import com.github.DNAProject.common.Address;
import com.github.DNAProject.common.Helper;
import com.github.DNAProject.crypto.SignatureScheme;
import com.github.DNAProject.core.payload.DeployWasmCode;
import com.github.DNAProject.core.payload.InvokeWasmCode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class WasmVmTest {

    private DnaSdk dnaSdk;

    private Account payer;

    private String oep4Contract = "c3304fc12dbc38aa5c8e308b9236552523ffae0f";

    @Before
    public void setUp() throws Exception {
        dnaSdk = DnaSdk.getInstance();
        dnaSdk.setRestful("http://127.0.0.1:20334");
        dnaSdk.setDefaultConnect(dnaSdk.getRestful());
//        ontSdk.openWalletFile("NeoVmTest.json");
        payer = new Account(Helper.hexToBytes(DnaSdkTest.PRIVATEKEY), SignatureScheme.SHA256WITHECDSA);
    }

    @Test
    public void makeDeployCodeTx() throws Exception {
        String code = "0061736d01000000014c0d60037f7f7f017f60027f7f017f6000017f60017f0060027f7f0060017f017f60037f7f7f0060000060047f7f7f7f0060027e7f017f60017f017e60057f7f7f7f7f017f60047f7f7f7f017f02550403656e76126f6e74696f5f696e7075745f6c656e677468000203656e760f6f6e74696f5f6765745f696e707574000303656e760c6f6e74696f5f72657475726e000403656e760b6f6e74696f5f70616e69630004033837030305040607050607030804040500000507070404060003030001010401040903030a0b0c040001010101010405030101010401000000040501700114140504010101500608017f01418080020b070a0106696e766f6b6500090919010041010b132e2b212c33343504371c1d1e1f1b2f2526322d0a8b393702000b7b01037f230041106b220124000240410b100622020d00410b41011007000b2001420b3702042001200236020020014100410b1008200020012903003702002001280200200128020822026a220341002900bc8002370000200041086a2002410b6a360200200341076a41002800c38002360000200141106a24000b0600200010110b040000000b7b01017f0240024002402000280204220320016b20024f0d00200120026a22022001490d01200341017422012002200120024b1b22014100480d010240024020030d002001100621020c010b200028020020032001101221020b2002450d0220002001360204200020023602000b0f0b1016000b200141011007000bd00402047f017e230041306b22002400410121010240024002400240024002400240024002400240024010002202450d002002417f4c0d012002100a2201450d02200110010b410021032000410036020820002002360204200020013602002002450d0620012d00002102200041013602080240200241837e6a220141024d0d002002ad21040c080b024002400240024020010e03000102000b200041206a20004102100b20002d00204101460d08200041286a28020041014d0d0520002802243300002104410321020c020b200041206a20004104100b20002d00204101460d07200041286a28020041034d0d0520002802243500002104410521020c010b200041206a20004108100b20002d00204101460d06200041206a41086a28020041074d0d0520002802242900002104410921020b41012103200442fd01540d064103410541092004428080808010541b200442808004541b2002460d070c060b100c000b200241011007000b41f08802100d000b41808a02100d000b41b88a02100d000b20002d002121030b200041013a0020200020033a00210c010b200041206a20002004a7100b20002d00204101470d0120002d002121030b200020033a001041808002412b200041106a41ac8002100e000b200041286a28020021022000280224210302400240410c10062201450d002000420c37021420002001360210024020024105470d00200341c78002460d02200341c780024105103a450d020b41808102100d000b410c41011007000b200041206a1005200041106a200041206a100f200028021020002802181010000b0600200010140b830101037f024002400240024020012802042203200128020822046b2002490d00200420026a22052004490d0220032005490d032001280200210320012005360208200041086a2002360200200041046a200320046a360200410021010c010b200041003a0001410121010b200020013a00000f0b200420051029000b200520031020000b05001016000b5b02017f037e230041306b220124002000290208210220002902102103200029020021042001420437031020014201370204200120043703182001200141186a36020020012003370328200120023703202001200141206a1022000b7f01017f230041c0006b220424002004200136020c2004200036020820042003360214200420023602102004412c6a41023602002004413c6a41013602002004420237021c200441c48702360218200441023602342004200441306a3602282004200441106a3602382004200441086a360230200441186a41d887021022000bb90101027f20012802002102024002402001280208220141fd01490d000240200141ffff03490d00200041fe011030200020002802084104100820002000280208220341046a360208200320002802006a20013600000c020b200041fd011030200020002802084102100820002000280208220341026a360208200320002802006a20013b00000c010b2000200110300b200020002802082001100820002000280208220320016a360208200320002802006a2002200110391a0b0900200020011002000b0600200010310b0a0020002001200210130b2301017f0240200210312203450d002003200020022001200120024b1b10391a0b20030b1b01017f0240200010312201450d0020014100200010381a0b20010b05001016000b090041988102100d000b800101027f02400240024020002802042202200028020822036b20014f0d00200320016a22012003490d02200241017422032001200320014b1b22014100480d020240024020020d002001100621020c010b200028020020022001101221020b2002450d0120002001360204200020023602000b0f0b200141011007000b1016000b830301067f230041306b2202240020012802002103024002402001280204220441037422050d00410021060c010b200341046a2107410021060340200728020020066a2106200741086a2107200541786a22050d000b0b024002400240024002400240200141146a2802000d00200621070c010b024020040d0041c48102410041001019000b024002402006410f4b0d002003280204450d010b200620066a220720064f0d010b4101210541002107200241086a21060c010b2007417f4c0d01200241086a2106024020070d0041012105410021070c010b200710062205450d020b200241003602102002200736020c200220053602082002200241086a360214200241186a41106a200141106a290200370300200241186a41086a200141086a29020037030020022001290200370318200241146a41d48102200241186a101a0d0220002006290200370200200041086a200641086a280200360200200241306a24000f0b1015000b200741011007000b41ec81024133200241186a41a08202100e000b6b01017f230041306b2203240020032002360204200320013602002003411c6a41023602002003412c6a41033602002003420237020c200341f88202360208200341033602242003200341206a360218200320033602282003200341046a360220200341086a20001022000bb90801087f230041c0006b22032400200341246a2001360200200341346a200241146a2802002204360200200341033a00382003412c6a2002280210220520044103746a36020020034280808080800437030820032000360220410021062003410036021820034100360210200320053602302003200536022802400240024002400240200228020822070d0020022802002108200228020422092004200420094b1b220a450d0141012104200020082802002008280204200128020c1100000d04200841086a210241012106034002402005280200200341086a200541046a280200110100450d00410121040c060b2006200a4f0d02200241046a210020022802002101200541086a2105200241086a210241012104200641016a2106200328022020012000280200200328022428020c110000450d000c050b0b20022802002108200228020422092002410c6a2802002205200520094b1b220a450d0041012104200020082802002008280204200128020c1100000d03200741106a2105200841086a21024101210603402003200541786a28020036020c2003200541106a2d00003a003820032005417c6a28020036020841002101410021040240024002400240200541086a2802000e0400010203000b2005410c6a2802002100410121040c020b02402005410c6a2802002207200328023422044f0d0041002104200328023020074103746a22072802044104470d0220072802002802002100410121040c020b41b88602200720041019000b4100210420032802282207200328022c460d002003200741086a3602284100210420072802044104470d0020072802002802002100410121040b2003200036021420032004360210024002400240024002400240024020052802000e0404010006040b20032802282200200328022c470d010c050b200541046a2802002200200328023422044f0d01200328023020004103746a22002802044104470d04200028020028020021040c030b2003200041086a36022820002802044104470d03200028020028020021040c020b41b88602200020041019000b200541046a28020021040b410121010b2003200436021c2003200136021802400240200541706a2802004101460d0020032802282204200328022c460d042003200441086a3602280c010b200541746a2802002204200328023422004f0d04200328023020044103746a21040b02402004280200200341086a200441046a280200110100450d00410121040c050b2006200a4f0d01200241046a210020022802002101200541246a2105200241086a210241012104200641016a2106200328022020012000280200200328022428020c110000450d000c040b0b0240200920064d0d00410121042003280220200820064103746a22052802002005280204200328022428020c1100000d030b410021040c020b41a08602100d000b41c88602200420001019000b200341c0006a240020040b02000b02000b2f01017f200028020022002002101720002000280208220320026a360208200320002802006a2001200210391a41000bcb0201027f230041106b220224002000280200210002400240024002402001418001490d002002410036020c2001418010490d0102402001418080044f0d0020022001413f71418001723a000e20022001410676413f71418001723a000d20022001410c76410f7141e001723a000c410321010c030b20022001413f71418001723a000f2002200141127641f001723a000c20022001410676413f71418001723a000e20022001410c76413f71418001723a000d410421010c020b0240200028020822032000280204470d00200041011017200028020821030b200028020020036a20013a00002000200028020841016a3602080c020b20022001413f71418001723a000d20022001410676411f7141c001723a000c410221010b20002001101720002000280208220320016a360208200320002802006a2002410c6a200110391a0b200241106a240041000b6201017f230041206b2202240020022000280200360204200241086a41106a200141106a290200370300200241086a41086a200141086a29020037030020022001290200370308200241046a41d48102200241086a101a2101200241206a240020010b6d01017f230041306b2202240020022001360204200220003602002002411c6a41023602002002412c6a41033602002002420237020c200241948502360208200241033602242002200241206a3602182002200241046a36022820022002360220200241086a41a485021022000b0b002000350200200110230b4602017f017e230041206b2202240020012902002103200241146a20012902083702002002200337020c200220003602082002418883023602042002410136020020021024000bcd0203027f017e037f230041306b22022400412721030240024020004290ce005a0d00200021040c010b412721030340200241096a20036a2205417c6a200020004290ce0080220442f0b17f7e7ca7220641ffff037141e4006e220741017441ca83026a2f00003b00002005417e6a2007419c7f6c20066a41ffff037141017441ca83026a2f00003b00002003417c6a2103200042ffc1d72f5621052004210020050d000b0b02402004a7220541e3004c0d00200241096a2003417e6a22036a2004a7220641ffff037141e4006e2205419c7f6c20066a41ffff037141017441ca83026a2f00003b00000b024002402005410a480d00200241096a2003417e6a22036a200541017441ca83026a2f00003b00000c010b200241096a2003417f6a22036a200541306a3a00000b200141ac88024100200241096a20036a412720036b10272103200241306a240020030bbe0201027f230041e0006b220124000240024002400240200028020822020d00200141003602300c010b20012002360200200141dc006a41013602002001420137024c200141a48802360248200141053602242001200141206a36025820012001360220200141306a200141c8006a101820012802300d010b20014100360208200142013703000c010b200141086a200141306a41086a280200360200200120012903303703000b200041146a28020021022001200029020c3703102001200236021c200141306a41146a4103360200200141c8006a41146a4103360200200141d4006a410636020020014203370234200141ac88023602302001410736024c2001200141c8006a36024020012001411c6a3602582001200141106a36025020012001360248200141206a200141306a1018200128022020012802281036000b02000b0c00428fa2e88daad0abe05a0bc50501067f20002802002205410171220620046a21070240024020054104710d00410021010c010b4100210802402002450d00200221092001210a03402008200a2d000041c00171418001466a2108200a41016a210a2009417f6a22090d000b0b200720026a20086b21070b412b418080c40020061b21080240024020002802084101460d004101210a200020082001200210280d012000280218200320042000411c6a28020028020c1100000f0b02402000410c6a280200220920074b0d004101210a200020082001200210280d012000280218200320042000411c6a28020028020c1100000f0b0240024020054108710d00200920076b21094100210a024002400240410120002d0030220720074103461b0e0402000100020b2009210a410021090c010b2009410176210a200941016a41017621090b200a41016a210a0340200a417f6a220a450d0220002802182000280204200028021c280210110100450d000b41010f0b4101210a200041013a003020004130360204200020082001200210280d01200920076b21084100210a024002400240410120002d0030220920094103461b0e0402000100020b2008210a410021080c010b2008410176210a200841016a41017621080b200a41016a210a02400340200a417f6a220a450d0120002802182000280204200028021c280210110100450d000b41010f0b200028020421094101210a200028021820032004200028021c28020c1100000d01200841016a2108200028021c210220002802182100034002402008417f6a22080d0041000f0b4101210a200020092002280210110100450d000c020b0b200028020421074101210a200020082001200210280d00200028021820032004200028021c28020c1100000d00200941016a2108200028021c210920002802182100034002402008417f6a22080d0041000f0b4101210a200020072009280210110100450d000b0b200a0b5401017f024002402001418080c400460d0041012104200028021820012000411c6a2802002802101101000d010b024020020d0041000f0b2000280218200220032000411c6a28020028020c11000021040b20040b6d01017f230041306b2202240020022001360204200220003602002002411c6a41023602002002412c6a41033602002002420237020c200241dc8502360208200241033602242002200241206a3602182002200241046a36022820022002360220200241086a41ec85021022000b8307010c7f200028021021030240024002400240200028020822044101460d0020030d012000280218200120022000411c6a28020028020c11000021030c030b2003450d010b0240024020020d00410021020c010b200120026a2105200041146a28020041016a21064100210720012103200121080340200341016a210902400240024020032c0000220a417f4a0d000240024020092005470d004100210b200521030c010b20032d0001413f71210b200341026a220921030b200a411f71210c0240200a41ff0171220a41df014b0d00200b200c41067472210a0c020b0240024020032005470d004100210d2005210e0c010b20032d0000413f71210d200341016a2209210e0b200d200b41067472210b0240200a41f0014f0d00200b200c410c7472210a0c020b02400240200e2005470d004100210a200921030c010b200e41016a2103200e2d0000413f71210a0b200b410674200c411274418080f0007172200a72220a418080c400470d020c040b200a41ff0171210a0b200921030b02402006417f6a2206450d00200720086b20036a21072003210820052003470d010c020b0b200a418080c400460d00024002402007450d0020072002460d0041002103200720024f0d01200120076a2c00004140480d010b200121030b2007200220031b21022003200120031b21010b20040d002000280218200120022000411c6a28020028020c1100000f0b4100210902402002450d002002210a200121030340200920032d000041c00171418001466a2109200341016a2103200a417f6a220a0d000b0b0240200220096b200028020c2206490d002000280218200120022000411c6a28020028020c1100000f0b410021074100210902402002450d00410021092002210a200121030340200920032d000041c00171418001466a2109200341016a2103200a417f6a220a0d000b0b200920026b20066a210a024002400240410020002d0030220320034103461b0e0402000100020b200a21074100210a0c010b200a4101762107200a41016a410176210a0b200741016a2103024003402003417f6a2203450d0120002802182000280204200028021c280210110100450d000b41010f0b2000280204210941012103200028021820012002200028021c28020c1100000d00200a41016a2103200028021c210a20002802182100034002402003417f6a22030d0041000f0b20002009200a280210110100450d000b41010f0b20030b1000200120002802002000280204102a0b0b002000350200200110230b1b00200128021841b68702410e2001411c6a28020028020c1100000b140020002802002001200028020428020c1101000b1b00200128021841fd870241052001411c6a28020028020c1100000b3f01017f0240200028020822022000280204470d002000200241011008200028020821020b200028020020026a20013a00002000200028020841016a3602080bd30101047f230041106b22012400024041002802a08b020d004100417f3602a08b020240024041002802a48b02220220006a220341002802a88b0222044d0d000240200320046b41ffff036a220441107640002202417f470d00410021020c020b20024110742103024041002802a48b0222020d00410020033602a48b02200321020b4100200320044180807c716a3602a88b02200220006a21030b410020033602a48b020b410041002802a08b0241016a3602a08b02200141106a240020020f0b418288024110200141086a41948802100e000b02000b6901037f230041206b220224002001411c6a280200210320012802182104200241086a41106a2000280200220141106a290200370300200241086a41086a200141086a2902003703002002200129020037030820042003200241086a101a2101200241206a240020010b1000200120002802002000280204102a0b1000200120002802002000280208102a0b0900200020011003000bd00101017f230041106b22022400024002400240024020002d00000e03010200010b2002200128021841c98802410b2001411c6a28020028020c11000022003a000820022001360200200241003a0009200241003602040c020b2002200128021841d48802410d2001411c6a28020028020c11000022003a000820022001360200200241003a0009200241003602040c010b2002200128021841e18802410d2001411c6a28020028020c11000022003a000820022001360200200241003a0009200241003602040b200241106a240020000b2c01017f02402002450d00200021030340200320013a0000200341016a21032002417f6a22020d000b0b20000b3601017f02402002450d00200021030340200320012d00003a0000200341016a2103200141016a21012002417f6a22020d000b0b20000b4a01037f4100210302402002450d000240034020002d0000220420012d00002205470d01200141016a2101200041016a21002002417f6a2202450d020c000b0b200420056b21030b20030b0ba80b0100418080020b9f0b63616c6c65642060526573756c743a3a756e77726170282960206f6e20616e2060457272602076616c7565000800000001000000010000000900000068656c6c6f20776f726c6468656c6c6f756e737570706f7274656420616374696f6e216578616d706c65732f68656c6c6f776f726c642f7372632f6c69622e72730000004c800000130000005f8000001e000000140000000e000000b080000011000000708500001700000009030000050000006361706163697479206f766572666c6f77000000308100004600000063010000130000000a00000004000000040000000b0000000c0000000d0000006120666f726d617474696e6720747261697420696d706c656d656e746174696f6e2072657475726e656420616e206572726f72000e00000000000000010000000f0000002f72757374632f373937393031366166663534356637623431636335313730333130323630323062333430393839642f7372632f6c6962636f72652f666d742f6d6f642e727300009881000020000000b88100001200000010000000000000000100000011000000696e646578206f7574206f6620626f756e64733a20746865206c656e20697320206275742074686520696e6465782069732030303031303230333034303530363037303830393130313131323133313431353136313731383139323032313232323332343235323632373238323933303331333233333334333533363337333833393430343134323433343434353436343734383439353035313532353335343535353635373538353936303631363236333634363536363637363836393730373137323733373437353736373737383739383038313832383338343835383638373838383939303931393239333934393539363937393839390000b482000006000000ba820000220000008785000018000000180a000005000000696e64657820206f7574206f662072616e676520666f7220736c696365206f66206c656e67746820fc82000016000000128300000d00000087850000180000001e0a000005000000736c69636520696e64657820737461727473206174202062757420656e64732061742000768300002b000000a1830000150000007a01000015000000608300001600000060040000110000006083000016000000540400002800000000000000000000007372632f6c6962636f72652f666d742f6d6f642e727363616c6c656420604f7074696f6e3a3a756e77726170282960206f6e206120604e6f6e65602076616c75657372632f6c6962636f72652f6f7074696f6e2e7273426f72726f774d75744572726f722c84000000000000d4830000020000003a200000e8830000150000008d040000050000007372632f6c6962636f72652f726573756c742e72734572726f72616c726561647920626f72726f7765640000120000000000000001000000130000002c840000000000002c8400000000000044840000040000004884000001000000206174203a496e76616c696455746638556e6578706563746564454f464972726567756c61724461746100008884000020000000a8840000550000007f08000009000000617373657274696f6e206661696c65643a2032203c3d206275662e6c656e28292f686f6d652f6e6173682f2e636172676f2f72656769737472792f7372632f6769746875622e636f6d2d316563633632393964623965633832332f627974656f726465722d312e332e322f7372632f6c69622e72730000001885000020000000a8840000550000008408000009000000617373657274696f6e206661696c65643a2034203c3d206275662e6c656e28295085000020000000a8840000550000008908000009000000617373657274696f6e206661696c65643a2038203c3d206275662e6c656e28297372632f6c6962616c6c6f632f7261775f7665632e72737372632f6c6962636f72652f736c6963652f6d6f642e7273";
        String contractHash = "259eed30d30d7c473944760023fcc321b8966371";
        Assert.assertEquals(contractHash, Address.AddressFromVmCode(code).toHexString());
        DeployWasmCode tx = dnaSdk.wasmvm().makeDeployCodeTransaction(code, "helloWorld", "1.0", "NashMiao",
                "wdx7266@vip.qq.com", "wasm contract for java sdk test", payer.getAddressU160(),
                500, 25000000);
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals(1, result.getIntValue("State"));
    }

    @Test
    public void makeAddInvokeCodeTx() throws Exception {
        String contractHash = "bf8ee176c360f7a77b9c45b6faab213bc50eaf5d";
        List<Object> params = new ArrayList<>(Arrays.asList(1, 2));
        InvokeWasmCode tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(contractHash, "add", params, payer.getAddressU160(), 500, 25000000);
        String targetPayload = "5daf0ec53b21abfab6459c7ba7f760c376e18ebf24036164640100000000000000000000000000000002000000000000000000000000000000";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals("03000000000000000000000000000000", result.getString("Result"));
        params = new ArrayList<>(Arrays.asList(-2, 3));
        tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(contractHash, "add", params, payer.getAddressU160(), 500, 25000000);
        targetPayload = "5daf0ec53b21abfab6459c7ba7f760c376e18ebf2403616464feffffffffffffffffffffffffffffff03000000000000000000000000000000";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals("01000000000000000000000000000000", result.getString("Result"));
    }

    @Test
    public void makeNotifyInvokeCodeTx() throws Exception {
        String contractHash = "bf8ee176c360f7a77b9c45b6faab213bc50eaf5d";
        List<Object> params = new ArrayList<>();
        InvokeWasmCode tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(contractHash, "notify", params, payer.getAddressU160(), 500, 25000000);
        String targetPayload = "5daf0ec53b21abfab6459c7ba7f760c376e18ebf07066e6f74696679";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals("hello", result.getJSONArray("Notify").getJSONObject(0).getJSONArray("States").get(0));
    }

    @Test
    public void makeBalanceOfInvokeCodeTx() throws Exception {
        List<Object> params = new ArrayList<>(Collections.singletonList(Address.decodeBase58("ANDfjwrUroaVtvBguDtrWKRMyxFwvVwnZD")));
        InvokeWasmCode tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(oep4Contract, "balanceOf", params, payer.getAddressU160(), 500, 25000000);
        String targetPayload = "0faeff23255536928b308e5caa38bc2dc14f30c31e0962616c616e63654f6646b1a18af6b7c9f8a4602f9f73eeb3030f0c29b7";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        System.out.println(JSON.toJSONString(result));
        //Assert.assertEquals("00e87648170000000000000000000000", result.getString("Result"));
    }

    @Test
    public void makeTotalSupplyInvokeCodeTx() throws Exception {
        List<Object> params = new ArrayList<>();
        InvokeWasmCode tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(oep4Contract, "totalSupply", params, payer.getAddressU160(), 500, 25000000);
        String targetPayload = "0faeff23255536928b308e5caa38bc2dc14f30c30c0b746f74616c537570706c79";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals("00e87648170000000000000000000000", result.getString("Result"));
    }

    @Test
    public void makeTransferInvokeCodeTx() throws Exception {
        List<Object> params = new ArrayList<>(Arrays.asList(payer.getAddressU160(), Address.decodeBase58("AazEvfQPcQ2GEFFPLF1ZLwQ7K5jDn81hve"), 10));
        InvokeWasmCode tx = dnaSdk.wasmvm().makeInvokeCodeTransaction(oep4Contract, "transfer", params, payer.getAddressU160(), 500, 25000000);
        String targetPayload = "0faeff23255536928b308e5caa38bc2dc14f30c341087472616e73666572d2c124dd088190f709b684e0bc676d70c41b3776d2c124dd088190f709b684e0bc676d70c41b37760a000000000000000000000000000000";
        Assert.assertEquals(targetPayload, Helper.toHexString(tx.invokeCode));
        dnaSdk.signTx(tx, new Account[][]{{payer}});
        JSONObject result = (JSONObject) dnaSdk.getRestful().sendRawTransactionPreExec(tx.toHexString());
        Assert.assertEquals("01", result.getString("Result"));
        Assert.assertEquals(1, result.getIntValue("State"));
        JSONObject notify = result.getJSONArray("Notify").getJSONObject(1);
        Assert.assertEquals(oep4Contract, notify.getString("ContractAddress"));
        Assert.assertEquals("7472616e73666572", notify.getJSONArray("States").getString(0));
        Assert.assertEquals("d2c124dd088190f709b684e0bc676d70c41b3776", notify.getJSONArray("States").getString(1));
        Assert.assertEquals("d2c124dd088190f709b684e0bc676d70c41b3776", notify.getJSONArray("States").getString(2));
        Assert.assertEquals("0a000000000000000000000000000000", notify.getJSONArray("States").getString(3));
    }

}
