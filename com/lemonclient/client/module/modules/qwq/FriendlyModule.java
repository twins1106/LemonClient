//Deobfuscated with https://github.com/SimplyProgrammer/Minecraft-Deobfuscator3000 using mappings "C:\Users\user\���� ȭ��\map"!

//Decompiled by Procyon!

package com.lemonclient.client.module.modules.qwq;

import com.lemonclient.client.module.*;
import com.lemonclient.api.setting.values.*;
import com.lemonclient.api.util.misc.*;
import java.util.*;

@Module.Declaration(name = "FriendlyModule", category = Category.qwq)
public class FriendlyModule extends Module
{
    IntegerSetting delay;
    int waited;
    String lastMeme;
    String lastMeme2;
    String lastMeme3;
    String lastMeme4;
    String lastMeme5;
    public static ArrayList<String> penshen;
    
    public FriendlyModule() {
        this.delay = this.registerInteger("Delay", 1, 0, 200);
        this.lastMeme = "";
        this.lastMeme2 = "";
        this.lastMeme3 = "";
        this.lastMeme4 = "";
        this.lastMeme5 = "";
    }
    
    public void onUpdate() {
        if (this.waited++ < (int)this.delay.getValue()) {
            return;
        }
        this.waited = 0;
        String phrase;
        for (phrase = FriendlyModule.penshen.get((int)(Math.random() * FriendlyModule.penshen.size())); phrase.equalsIgnoreCase(this.lastMeme) || phrase.equalsIgnoreCase(this.lastMeme2) || phrase.equalsIgnoreCase(this.lastMeme3) || phrase.equalsIgnoreCase(this.lastMeme4) || phrase.equalsIgnoreCase(this.lastMeme5); phrase = FriendlyModule.penshen.get((int)(Math.random() * FriendlyModule.penshen.size()))) {}
        MessageBus.sendServerMessage(phrase);
        this.lastMeme = this.lastMeme2;
        this.lastMeme2 = this.lastMeme3;
        this.lastMeme3 = this.lastMeme4;
        this.lastMeme4 = this.lastMeme5;
        this.lastMeme5 = phrase;
    }
    
    static {
        FriendlyModule.penshen = new ArrayList<String>(Arrays.asList("\u8fd9\u4e48\u56a3\u5f20\uff0c\u662f\u6709\u52a8\u7269\u4fdd\u62a4\u534f\u4f1a\u6491\u8170\u5417\uff1f", "\u82f1\u6587\u540d\u53ebCtrlC\u8fd8CtrlV\u554a\uff1f\u8fd9\u4e48\u7231\u5b66\u4eba", "\u613f\u75c5\u9b54\u65e9\u65e5\u6218\u80dc\u4f60\u5168\u5bb6", "\u77e5\u9053\u4f60\u662f\u4e2a\u76f4\u80a0\u5b50\uff0c\u4f46\u4f60\u4e5f\u4e0d\u80fd\u7528\u5634\u62c9\u5427", "\u770b\u60a8\u8bf4\u8bdd\u8fd9\u903b\u8f91\uff0c\u76f4\u80a0\u901a\u5927\u8111\u5427", "\u4eba\u7c7b\u8fdb\u5316\u7684\u65f6\u5019\uff0c\u4f60\u8eb2\u8d77\u6765\u4e86\u5417", "\u4f60\u8981\u662f\u6709\u75c5\u522b\u6765\u627e\u6211\uff0c\u6211\u53c8\u4e0d\u662f\u517d\u533b", "\u4f60\u7edd\u5bf9\u662f\u4f60\u5988\u4eb2\u751f\u7684\uff0c\u4e0d\u7136\u4f60\u5988\u600e\u4e48\u4f1a\u517b\u4f60\u8fd9\u4e48\u4e2a\u6df7\u8d26\u4e1c\u897f\uff01", "\u4f60\u73a9\u5288\u817f\uff0c\u5288\u90a3\u4e48\u5f00\uff0c\u4e0d\u6015\u86cb\u86cb\u53d7\u51c9\u554a?", "\u7b49\u6211\u6709\u94b1\u4e86\uff0c\u6211\u5c31\u5e26\u4f60\u53bb\u6700\u597d\u7684\u7cbe\u795e\u75c5\u9662\u3002", "\u6211\u4e5f\u4e0d\u60f3\u6253\u51fb\u4f60\u4e86\u3002\u4f60\u53bb\u52a8\u7269\u56ed\u770b\u770b\u6709\u6ca1\u6709\u9002\u5408\u7684\u5de5\u4f5c\u9002\u5408\u4f60\uff0c\u4f60\u8fd9\u6837\u5728\u8857\u4e0a\u4e71\u8dd1\u5f88\u5bb9\u6613\u88ab\u8b66\u5bdf\u5c04\u6740\u7684\u3002", "\u62ff\u4f60\u5f53\u4eba\u7684\u65f6\u540e\uff0c\u4f60\u5c3d\u91cf\u88c5\u7684\u50cf\u70b9\u597d\u5417\u3002", "\u770b\u5230\u4e86\u4f60\uff0c\u6211\u7a81\u7136\u5c31\u660e\u767d\u4e86\u6bd5\u52a0\u7d22\u662f\u4e2a\u600e\u6837\u7684\u5b58\u5728\u3002", "\u4f60\u5988\u751f\u4f60\u90a3\u5929\uff0c\u4f60\u4e00\u4e2a\u7239\u90fd\u6ca1\u6765", "\u4f60\u4e11\u7684\u6211\u90fd\u5931\u53bb\u77e5\u89c9\u4e86", "\u6211\u6253\u4e86\u72c2\u72ac\u75ab\u82d7\uff0c\u4f60\u4ee5\u4e3a\u6211\u4f1a\u6015\u4f60\uff1f", "\u867d\u7136\u4f60\u50bb\u903c\uff0c\u4f46\u4f60\u5728\u505a\u81ea\u5df1\uff0c\u4e3a\u4f60\u611f\u5230\u5f00\u5fc3", "\u5982\u679c\u4e0d\u80fd\u8bf4\u810f\u8bdd\uff0c\u90a3\u6211\u5bf9\u4f60\u4e5f\u6ca1\u8bdd\u53ef\u8bf4", "\u522b\u8ddf\u6211\u8bf4\u8bdd\uff0c\u6211\u6709\u6d01\u7656", "\u4eca\u5929\u60f3\u9a82\u4eba\uff0c\u6240\u4ee5\u4e0d\u9a82\u4f60", "\u4f60\u4eec\u5bb6\u90fd\u662f\u4f60\u505a\u996d\u5427\uff0c\u6211\u770b\u4f60\u633a\u4f1a\u6dfb\u6cb9\u52a0\u918b\u7684", "\u6211\u662f\u7b97\u547d\u7684\uff0c\u8bf7\u95ee\u60a8\u7b97\u4ec0\u4e48\u4e1c\u897f\uff1f", "\u7ef3\u5b50\u592a\u957f\u4f1a\u6253\u7ed3\uff0c\u4f60\u7684\u820c\u5934\u548b\u4e0d\u80fd\u5462\uff1f", "\u795d\u4f60\u7684\u5a5a\u793c\u8d8a\u529e\u8d8a\u597d", "\u4f60\u662f\u4e9a\u91cc\u58eb\u591a\u5fb7\u7684\u59b9\u59b9\uff0c\u73cd\u59ae\u739b\u58eb\u591a\u5417", "\u7528\u4e00\u53e5\u8bdd\u5f62\u5bb9\u4f60\u5427\uff0c\u4eba\u9762\u4e0d\u77e5\u4f55\u5904\u53bb", "\u4eca\u591c\u7684\u98ce\u6709\u70b9\u5927\uff0c\u4e0d\u77e5\u9053\u4f60\u68fa\u6750\u6f0f\u4e0d\u6f0f\u98ce", "\u4ee5\u540e\u4f60\u513f\u5b59\u6ee1\u5802\uff0c\u5168\u9760\u5144\u5f1f\u5e2e\u5fd9", "\u770b\u4f60\u633a\u51f6\u7684\uff0c\u6211\u7684QQ\u519c\u573a\u7f3a\u6761\u72d7\uff0c\u660e\u5929\u6765\u4e0a\u73ed\u5427", "\u60a8\u914d\u94a5\u5319\u5417\uff1f\u60a8\u914d\u5417\uff1f\u60a8\u914d\u51e0\u628a\uff1f", "\u90a3\u4e48\u559c\u6b22\u88c5\u903c\uff0c\u5f53\u5185\u88e4\u7b97\u4e86", "\u795d\u60a8\u5bff\u6bd4\u6619\u82b1", "\u771f\u7fa1\u6155\u4f60\u7684\u76ae\u80a4\uff0c\u4fdd\u517b\u7684\u8fd9\u4e48\u539a", "\u4f60\u4e00\u6765\uff0c\u6211\u8fd9\u4e00\u9505\u7ca5\u90fd\u4e0d\u80fd\u8981\u4e86", "\u6bcf\u5f53\u60f3\u8d77\u4f60\uff0c\u6211\u7684\u4e2d\u6307\u5c31\u5fcd\u4e0d\u4f4f\u52c3\u8d77", "\u88c5GPS\u4e86\u5417\uff1f\u6e05\u695a\u81ea\u5df1\u7684\u5b9a\u4f4d\u5417\uff1f", "\u5927\u592b\u8bf4\u4f60\u8fd9\u75c5\uff0c\u4ece\u7709\u6bdb\u5f80\u4e0b\u90fd\u5f97\u622a\u80a2", "\u4f60\u8981\u597d\u597d\u505a\u81ea\u5df1\uff0c\u53cd\u6b63\u522b\u7684\u4f60\u4e5f\u505a\u4e0d\u597d", "\u8349\u8239\u4e0a\u501f\u7684\u4e0d\u5c31\u662f\u4f60\u5417\uff1f", "\u6211\u6709\u5bc6\u96c6\u6050\u60e7\u75c7\uff0c\u4e0d\u80fd\u63a5\u8fd1\u5fc3\u773c\u5b50\u591a\u7684\u4eba", "\u6211\u5b66\u4f1a\u4e86\u548c\u755c\u751f\u6c9f\u901a\uff0c\u6211\u4eec\u7ec8\u4e8e\u53ef\u4ee5\u804a\u5929\u5566", "\u521a\u624d\u542c\u5230\u7caa\u8f66\u7206\u70b8\u4e86\uff0c\u539f\u6765\u662f\u4f60\u5f00\u53e3\u8bf4\u8bdd\u4e86", "\u6211\u548c\u4f60\u6bd4\u4e5f\u5c31\u591a\u5f20\u8138", "\u8fd9\u4e48\u559c\u6b22\u5230\u5904\u8ba4\u513f\u5b50\uff0c\u662f\u6ca1\u6709\u751f\u80b2\u80fd\u529b\uff1f", "\u4f60\u7684\u8116\u5b50\u771f\u53ef\u7231\uff0c\u4e0a\u9762\u9876\u4e2a\u732a\u8111\u888b", "\u4f60\u662f\u53e4\u73a9\u57ce\u8001\u677f\u5417\uff1f\u89c1\u8c01\u90fd\u53eb\u5b9d\u8d1d", "\u4f60\u662f\u4ec0\u4e48\u54c1\u79cd\u7684\u732a\u600e\u4e48\u8fd9\u4e48\u51f6", "\u4f60\u957f\u8111\u888b\u53ea\u662f\u4e3a\u4e86\u8ba9\u81ea\u5df1\u770b\u8d77\u6765\u9ad8\u70b9\u4e48", "\u4e3a\u4ec0\u4e48\u4e0d\u548c\u6211\u8bf4\u8bdd\uff1f\u662f\u56e0\u4e3a\u4f60\u4e0d\u914d\u5417\uff1f", "\u4f60\u7684\u957f\u76f8\u5f88\u63d0\u795e", "\u4f60\u771f\u806a\u660e\uff0c\u5c45\u7136\u8fd8\u77e5\u9053\u81ea\u5df1\u662f\u4e2a\u4eba", "\u5403\u4e8f\u662f\u798f\uff0c\u6211\u795d\u4f60\uff0c\u798f\u5982\u4e1c\u6d77", "\u4f60\u7785\u4f60\u90a3\u4e94\u5b98\uff0c\u5404\u957f\u5404\u7684\uff0c\u8c01\u90fd\u4e0d\u670d\u8c01", "\u54ea\u4e2a\u4e0b\u6c34\u9053\u6ca1\u76d6\u597d\uff0c\u53c8\u8ba9\u4f60\u722c\u51fa\u6765\u4e86", "\u4f60\u7684\u620f\u53ef\u4ee5\u50cf\u4f60\u7684\u94b1\u4e00\u6837\u5c11\u5417?", "\u4f60\u662f\u4ec0\u4e48\u724c\u5b50\u5851\u6599\u888b\uff0c\u8fd9\u4e48\u80fd\u88c5", "\u4f60\u4e0d\u53bb\u5f53\u53a8\u5b50\u53ef\u60dc\u4e86\uff0c\u7529\u9505\u7529\u7684\u90a3\u4e48\u5389\u5bb3", "\u4f60\u662f\u4e00\u6761\u9178\u83dc\u9c7c\uff0c\u53c8\u9178\u53c8\u83dc\u53c8\u591a\u9c7c", "\u6211\u4e0d\u60f3\u77e5\u9053\u4f60\u6709\u75c5\uff0c\u522b\u8868\u73b0\u7684\u8fd9\u4e48\u660e\u663e\u597d\u5417", "\u597d\u60f3\u4e3a\u4ed6\u54ed \u53ef\u4ed6\u5c31\u662f\u4e0d\u6b7b", "\u90fd\u662f\u6211\u7684\u9519\uff0c\u53c8\u628a\u4f60\u5f53\u4eba\u770b\u4e86\u3002", "\u4f60\u90a3\u4e48\u5389\u5bb3\uff0c\u4e00\u5b9a\u662f\u81ea\u5df1\u4e00\u4e2a\u4eba\u957f\u5927\u7684\u5427\uff1f", "\u6211\u53ef\u4e0d\u6562\u78b0\u60a8\uff0c\u6211\u5bb6\u7684\u6d17\u624b\u6db2\u524d\u4e24\u5929\u7528\u5b8c\u4e86\u3002", "\u8bf7\u4f60\u4e0d\u8981\u548c\u6211\u8c08\u4eba\u751f\uff0c\u56e0\u4e3a\u4f60\u538b\u6839\u5c31\u4e0d\u662f\u4eba\u751f\u7684\u3002", "\u6bcf\u6b21\u770b\u5230\u8def\u8fb9\u7684\u5783\u573e\uff0c\u6211\u603b\u80fd\u60f3\u8d77\u4f60\u3002", "\u6211\u628a\u4f60\u5f53\u670b\u53cb\uff0c\u4f60\u600e\u4e48\u53ef\u4ee5\u628a\u6211\u5f53\u7236\u4eb2\uff1f", "\u5934\u6761\u641c\u7d22\u4e0d\u5230\u4f60\uff0c\u53ef\u4ee5\u8bd5\u8bd5\u641c\u72d7\u3002", "\u4f60\u8bf4\u8bdd\u90a3\u4e48\u597d\u542c\uff0c\u4e0a\u5395\u6240\u4e00\u5b9a\u64e6\u8fc7\u5634\u4e86\u3002", "\u4f60\u5c31\u662f\u6211\u952e\u76d8\u4e0a\u5b57\u6bcdA\u4e0eD\u548cV\u4e0eN\u4e4b\u95f4\u90a3\u4e24\u4e2a\u5b57\u6bcd\u3002", "\u8bf7\u95ee\u4f60\u662f\u4ec0\u4e48\u724c\u5b50\u7684\u8ba1\u7b97\u673a\uff0c\u90a3\u4e48\u4f1a\u7b97\u8ba1\u3002", "\u5c11\u5403\u70b9\u76d0\uff0c\u770b\u60a8\u95f2\u7684\u3002", "\u4f60\u9e21\u7a9d\u91cc\u7684\u77f3\u5934\uff0c\u6df7\u86cb\u4e00\u4e2a\u3002", "\u5982\u679c\u6ca1\u6709\u4f60\uff0c\u8c01\u6765\u886c\u6258\u4ed6\u4eec\u7684\u7f8e\u3002", "\u6ca1\u60f3\u5230\u4f60\u8fd9\u5e74\u7eaa\u8f7b\u8f7b\u7684\uff0c\u5c31\u61c2\u5f97\u7528\u8138\u5413\u552c\u4eba\u4e86\u3002", "\u60a8\u5bb6\u6237\u53e3\u672c\u7ffb\u5f00\u5c31\u662f\u4e00\u672c\u52a8\u7269\u767e\u79d1\u3002", "\u4f60\u8fd9\u662f\u8001\u592a\u592a\u559d\u7a00\u7ca5\uff0c\u65e0\u803b\u53c8\u4e0b\u6d41\u3002", "\u9a6c\u6876\u91cc\u70b9\u706f\uff0c\u6401\u7740\u627e\u5c4e\u3002", "\u5403\u9c7c\u4e0d\u5410\u9aa8\u5934\uff0c\u8bf4\u8bdd\u5e26\u523a\u3002", "\u4f60\u662f\u4e0d\u77e5\u706b\u821e\u7684\u5f1f\u5f1f\u4e0d\u77e5\u597d\u6b79\u5427", "\u4f60\u662f7\u670815\u73a9\u7684\u592a\u5f00\u5fc3\u5fd8\u8bb0\u56de\u5bb6\u4e86\u5417\uff1f", "\u4f60\u662f\u6d17\u5934\u5f1f\u51fa\u8eab\u7684\u5417\uff1f\u6d17\u8111\u6d17\u7684\u8fd9\u4e48\u5389\u5bb3"));
    }
}
