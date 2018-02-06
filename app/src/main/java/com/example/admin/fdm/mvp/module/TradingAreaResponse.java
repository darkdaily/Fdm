package com.example.admin.fdm.mvp.module;

import java.util.List;

/**
 * Created by test on 2018/1/18.
 */

public class TradingAreaResponse extends BaseResponse<TradingAreaResponse.DataBean> {

    public static class DataBean extends BaseResponse.DataBean{


        /**
         * district : 回龙观
         * list : [{"id":"20","name":"昌平","province":"668","country":"654","sub":[{"id":"54","name":"回龙观","parent":"20"},{"id":"335","name":"天通苑","parent":"20"},{"id":"345","name":"北七家","parent":"20"},{"id":"346","name":"昌平县城","parent":"20"},{"id":"348","name":"霍营","parent":"20"},{"id":"349","name":"立水桥","parent":"20"},{"id":"350","name":"龙泽","parent":"20"},{"id":"351","name":"南口","parent":"20"},{"id":"352","name":"沙河","parent":"20"},{"id":"353","name":"小汤山","parent":"20"},{"id":"354","name":"百善镇","parent":"20"},{"id":"355","name":"阳坊","parent":"20"}]},{"id":"336","name":"朝阳","province":"668","country":"654","sub":[{"id":"337","name":"北苑","parent":"336"},{"id":"338","name":"望京","parent":"336"},{"id":"339","name":"亚运村","parent":"336"},{"id":"343","name":"双井","parent":"336"},{"id":"344","name":"劲松","parent":"336"},{"id":"356","name":"CBD","parent":"336"},{"id":"357","name":"安慧桥","parent":"336"},{"id":"358","name":"安贞","parent":"336"},{"id":"359","name":"奥林匹克公园","parent":"336"},{"id":"360","name":"百子湾","parent":"336"},{"id":"361","name":"北工大","parent":"336"},{"id":"362","name":"北沙滩","parent":"336"},{"id":"363","name":"常营","parent":"336"},{"id":"364","name":"朝青","parent":"336"},{"id":"365","name":"朝阳公园","parent":"336"},{"id":"366","name":"朝阳门","parent":"336"},{"id":"367","name":"大山子","parent":"336"},{"id":"368","name":"大望路","parent":"336"},{"id":"369","name":"定福庄","parent":"336"},{"id":"370","name":"东八里庄","parent":"336"},{"id":"371","name":"东坝","parent":"336"},{"id":"372","name":"东大桥","parent":"336"},{"id":"373","name":"豆各庄","parent":"336"},{"id":"374","name":"垡头","parent":"336"},{"id":"375","name":"甘露园","parent":"336"},{"id":"376","name":"高碑店","parent":"336"},{"id":"377","name":"工体","parent":"336"},{"id":"378","name":"管庄","parent":"336"},{"id":"379","name":"广渠门","parent":"336"},{"id":"380","name":"国贸","parent":"336"},{"id":"381","name":"国展","parent":"336"},{"id":"382","name":"红庙","parent":"336"},{"id":"383","name":"华威","parent":"336"},{"id":"384","name":"欢乐谷","parent":"336"},{"id":"385","name":"惠新西街","parent":"336"},{"id":"386","name":"建国门","parent":"336"},{"id":"387","name":"健翔桥","parent":"336"},{"id":"388","name":"京广","parent":"336"},{"id":"389","name":"酒仙桥","parent":"336"},{"id":"390","name":"来广营","parent":"336"},{"id":"391","name":"柳芳","parent":"336"},{"id":"392","name":"潘家园","parent":"336"},{"id":"393","name":"三里屯","parent":"336"},{"id":"394","name":"三元桥","parent":"336"},{"id":"395","name":"芍药居","parent":"336"},{"id":"396","name":"十八里店","parent":"336"},{"id":"397","name":"十里堡","parent":"336"},{"id":"398","name":"石佛营","parent":"336"},{"id":"399","name":"双桥","parent":"336"},{"id":"400","name":"四惠","parent":"336"},{"id":"401","name":"太阳宫","parent":"336"},{"id":"402","name":"团结湖","parent":"336"},{"id":"403","name":"西坝河","parent":"336"},{"id":"404","name":"亚运村小营","parent":"336"},{"id":"405","name":"燕莎","parent":"336"},{"id":"406","name":"左家庄","parent":"336"},{"id":"672","name":"十里河","parent":"336"},{"id":"673","name":"潘家园","parent":"336"},{"id":"674","name":"孙河","parent":"336"}]},{"id":"341","name":"海淀","province":"668","country":"654","sub":[{"id":"342","name":"魏公村","parent":"341"},{"id":"407","name":"安宁庄","parent":"341"},{"id":"408","name":"八里庄","parent":"341"},{"id":"409","name":"北京大学","parent":"341"},{"id":"410","name":"北太平庄","parent":"341"},{"id":"411","name":"厂洼","parent":"341"},{"id":"412","name":"车道沟","parent":"341"},{"id":"413","name":"大钟寺","parent":"341"},{"id":"414","name":"定慧寺","parent":"341"},{"id":"415","name":"二里庄","parent":"341"},{"id":"416","name":"甘家口","parent":"341"},{"id":"417","name":"公主坟","parent":"341"},{"id":"418","name":"航天桥","parent":"341"},{"id":"419","name":"花园桥","parent":"341"},{"id":"420","name":"蓟门桥","parent":"341"},{"id":"421","name":"军博","parent":"341"},{"id":"422","name":"马甸","parent":"341"},{"id":"423","name":"马连洼","parent":"341"},{"id":"424","name":"牡丹园","parent":"341"},{"id":"425","name":"农大","parent":"341"},{"id":"426","name":"清河","parent":"341"},{"id":"427","name":"清华园","parent":"341"},{"id":"428","name":"上地","parent":"341"},{"id":"429","name":"世纪城","parent":"341"},{"id":"430","name":"曙光","parent":"341"},{"id":"431","name":"双榆树","parent":"341"},{"id":"432","name":"四季青","parent":"341"},{"id":"433","name":"苏州桥","parent":"341"},{"id":"434","name":"田村","parent":"341"},{"id":"435","name":"万柳","parent":"341"},{"id":"436","name":"万泉河","parent":"341"},{"id":"437","name":"万寿寺","parent":"341"},{"id":"438","name":"五道口","parent":"341"},{"id":"439","name":"五棵松","parent":"341"},{"id":"440","name":"西北旺","parent":"341"},{"id":"441","name":"西二旗","parent":"341"},{"id":"442","name":"西三旗","parent":"341"},{"id":"443","name":"西山","parent":"341"},{"id":"444","name":"西直门外","parent":"341"},{"id":"445","name":"香山","parent":"341"},{"id":"446","name":"小西天","parent":"341"},{"id":"447","name":"学院路","parent":"341"},{"id":"448","name":"颐和园","parent":"341"},{"id":"449","name":"永定路","parent":"341"},{"id":"450","name":"玉泉路","parent":"341"},{"id":"451","name":"圆明园","parent":"341"},{"id":"452","name":"皂君庙","parent":"341"},{"id":"453","name":"增光路","parent":"341"},{"id":"454","name":"知春路","parent":"341"},{"id":"455","name":"中关村","parent":"341"},{"id":"456","name":"紫竹桥","parent":"341"}]},{"id":"457","name":"东城","province":"668","country":"654","sub":[{"id":"458","name":"安定门","parent":"457"},{"id":"459","name":"北新桥","parent":"457"},{"id":"460","name":"朝阳门内","parent":"457"},{"id":"461","name":"崇文门","parent":"457"},{"id":"462","name":"磁器口","parent":"457"},{"id":"463","name":"灯市口","parent":"457"},{"id":"464","name":"东单","parent":"457"},{"id":"465","name":"东花市","parent":"457"},{"id":"466","name":"东四","parent":"457"},{"id":"467","name":"东四十条","parent":"457"},{"id":"468","name":"东直门","parent":"457"},{"id":"469","name":"光明楼","parent":"457"},{"id":"470","name":"广渠门内","parent":"457"},{"id":"471","name":"和平里","parent":"457"},{"id":"472","name":"交道口","parent":"457"},{"id":"473","name":"金宝街","parent":"457"},{"id":"474","name":"龙潭湖","parent":"457"},{"id":"475","name":"前门","parent":"457"},{"id":"476","name":"天坛","parent":"457"},{"id":"477","name":"王府井","parent":"457"},{"id":"478","name":"新世界","parent":"457"},{"id":"479","name":"雍和宫","parent":"457"},{"id":"480","name":"永定门","parent":"457"}]},{"id":"481","name":"西城","province":"668","country":"654","sub":[{"id":"482","name":"白云路","parent":"481"},{"id":"483","name":"白纸坊","parent":"481"},{"id":"484","name":"车公庄","parent":"481"},{"id":"485","name":"大观园","parent":"481"},{"id":"486","name":"大栅栏","parent":"481"},{"id":"487","name":"德胜门","parent":"481"},{"id":"488","name":"地安门","parent":"481"},{"id":"489","name":"阜成门","parent":"481"},{"id":"490","name":"复兴门","parent":"481"},{"id":"491","name":"鼓楼大街","parent":"481"},{"id":"492","name":"官园","parent":"481"},{"id":"493","name":"广安门","parent":"481"},{"id":"494","name":"和平门","parent":"481"},{"id":"495","name":"虎坊桥","parent":"481"},{"id":"496","name":"积水潭","parent":"481"},{"id":"497","name":"金融街","parent":"481"},{"id":"498","name":"礼士路","parent":"481"},{"id":"499","name":"六铺炕","parent":"481"},{"id":"500","name":"马连道","parent":"481"},{"id":"501","name":"木樨地","parent":"481"},{"id":"502","name":"牛街","parent":"481"},{"id":"503","name":"三里河","parent":"481"},{"id":"504","name":"什刹海","parent":"481"},{"id":"505","name":"陶然亭","parent":"481"},{"id":"506","name":"天宁寺","parent":"481"},{"id":"507","name":"天桥","parent":"481"},{"id":"508","name":"西便门","parent":"481"},{"id":"509","name":"西单","parent":"481"},{"id":"510","name":"西四","parent":"481"},{"id":"511","name":"西直门","parent":"481"},{"id":"512","name":"先农坛","parent":"481"},{"id":"513","name":"小马厂","parent":"481"},{"id":"514","name":"新街口","parent":"481"},{"id":"515","name":"宣武门","parent":"481"},{"id":"516","name":"右安门内","parent":"481"},{"id":"517","name":"月坛","parent":"481"},{"id":"518","name":"展览路","parent":"481"},{"id":"519","name":"长椿街","parent":"481"}]},{"id":"520","name":"大兴","province":"668","country":"654","sub":[{"id":"521","name":"黄村北","parent":"520"},{"id":"522","name":"黄村东","parent":"520"},{"id":"523","name":"黄村南","parent":"520"},{"id":"524","name":"旧宫","parent":"520"},{"id":"525","name":"西红门","parent":"520"},{"id":"526","name":"亦庄","parent":"520"}]},{"id":"527","name":"房山","province":"668","country":"654","sub":[{"id":"528","name":"窦店","parent":"527"},{"id":"529","name":"房山城关","parent":"527"},{"id":"530","name":"韩村河","parent":"527"},{"id":"531","name":"行宫","parent":"527"},{"id":"532","name":"良乡","parent":"527"},{"id":"533","name":"琉璃河","parent":"527"},{"id":"534","name":"青龙湖","parent":"527"},{"id":"535","name":"太平庄","parent":"527"},{"id":"536","name":"闫村","parent":"527"},{"id":"537","name":"燕山","parent":"527"},{"id":"538","name":"长阳","parent":"527"},{"id":"539","name":"周口店","parent":"527"}]},{"id":"540","name":"丰台","province":"668","country":"654","sub":[{"id":"541","name":"北大地","parent":"540"},{"id":"542","name":"菜户营","parent":"540"},{"id":"543","name":"草桥","parent":"540"},{"id":"544","name":"成寿寺","parent":"540"},{"id":"545","name":"大红门","parent":"540"},{"id":"546","name":"东高地","parent":"540"},{"id":"547","name":"方庄","parent":"540"},{"id":"548","name":"丰台体育馆","parent":"540"},{"id":"549","name":"和义","parent":"540"},{"id":"550","name":"花乡","parent":"540"},{"id":"551","name":"角门","parent":"540"},{"id":"552","name":"京石高速","parent":"540"},{"id":"553","name":"看丹桥","parent":"540"},{"id":"554","name":"科丰桥","parent":"540"},{"id":"555","name":"科技园区","parent":"540"},{"id":"556","name":"丽泽桥","parent":"540"},{"id":"557","name":"刘家窑","parent":"540"},{"id":"558","name":"六里桥","parent":"540"},{"id":"559","name":"卢沟桥","parent":"540"},{"id":"560","name":"马家堡","parent":"540"},{"id":"561","name":"木樨园","parent":"540"},{"id":"562","name":"蒲黄榆","parent":"540"},{"id":"563","name":"青塔","parent":"540"},{"id":"564","name":"宋家庄","parent":"540"},{"id":"565","name":"西局","parent":"540"},{"id":"566","name":"西客站","parent":"540"},{"id":"567","name":"西罗园","parent":"540"},{"id":"568","name":"新发地","parent":"540"},{"id":"569","name":"洋桥","parent":"540"},{"id":"570","name":"右安门","parent":"540"},{"id":"571","name":"玉泉营","parent":"540"},{"id":"572","name":"岳各庄","parent":"540"},{"id":"573","name":"云岗","parent":"540"},{"id":"574","name":"长辛店","parent":"540"},{"id":"575","name":"赵公口","parent":"540"},{"id":"576","name":"左安门","parent":"540"},{"id":"675","name":"东大街","parent":"540"}]},{"id":"577","name":"怀柔","province":"668","country":"654","sub":[{"id":"578","name":"怀柔","parent":"577"}]},{"id":"579","name":"门头沟","province":"668","country":"654","sub":[{"id":"580","name":"城子","parent":"579"},{"id":"581","name":"大峪","parent":"579"},{"id":"582","name":"东辛房","parent":"579"},{"id":"583","name":"河滩","parent":"579"},{"id":"584","name":"圈门","parent":"579"},{"id":"585","name":"双峪","parent":"579"},{"id":"586","name":"西辛房","parent":"579"}]},{"id":"587","name":"密云","province":"668","country":"654","sub":[{"id":"588","name":"宾阳","parent":"587"},{"id":"589","name":"鼓楼","parent":"587"},{"id":"590","name":"密云","parent":"587"},{"id":"591","name":"密云果园","parent":"587"},{"id":"592","name":"沙河水库","parent":"587"},{"id":"593","name":"少年宫","parent":"587"}]},{"id":"594","name":"平谷","province":"668","country":"654","sub":[{"id":"595","name":"平谷","parent":"594"}]},{"id":"596","name":"石景山","province":"668","country":"654","sub":[{"id":"597","name":"八宝山","parent":"596"},{"id":"598","name":"八大处","parent":"596"},{"id":"599","name":"八角","parent":"596"},{"id":"600","name":"高井","parent":"596"},{"id":"601","name":"古城","parent":"596"},{"id":"602","name":"金顶街","parent":"596"},{"id":"603","name":"老古城","parent":"596"},{"id":"604","name":"老山","parent":"596"},{"id":"605","name":"鲁谷","parent":"596"},{"id":"606","name":"模式口","parent":"596"},{"id":"607","name":"苹果园","parent":"596"},{"id":"609","name":"杨庄","parent":"596"},{"id":"610","name":"永乐","parent":"596"}]},{"id":"611","name":"顺义","province":"668","country":"654","sub":[{"id":"612","name":"后沙峪","parent":"611"},{"id":"613","name":"机场南楼","parent":"611"},{"id":"614","name":"李桥","parent":"611"},{"id":"615","name":"马坡","parent":"611"},{"id":"616","name":"顺义城","parent":"611"},{"id":"617","name":"天竺","parent":"611"},{"id":"618","name":"杨镇","parent":"611"},{"id":"619","name":"中央别墅区","parent":"611"}]},{"id":"620","name":"通州","province":"668","country":"654","sub":[{"id":"621","name":"八里桥市场","parent":"620"},{"id":"622","name":"北关","parent":"620"},{"id":"623","name":"次渠","parent":"620"},{"id":"624","name":"东关","parent":"620"},{"id":"625","name":"果园","parent":"620"},{"id":"626","name":"焦王庄","parent":"620"},{"id":"627","name":"九棵树","parent":"620"},{"id":"628","name":"梨园","parent":"620"},{"id":"629","name":"潞苑南大街","parent":"620"},{"id":"630","name":"马驹桥","parent":"620"},{"id":"631","name":"乔庄","parent":"620"},{"id":"632","name":"通州北苑","parent":"620"},{"id":"633","name":"土桥","parent":"620"},{"id":"634","name":"武夷花园","parent":"620"},{"id":"635","name":"西马庄","parent":"620"},{"id":"636","name":"西上园","parent":"620"},{"id":"637","name":"新华大街","parent":"620"},{"id":"638","name":"永顺","parent":"620"},{"id":"639","name":"玉桥","parent":"620"},{"id":"640","name":"运河大街","parent":"620"},{"id":"641","name":"中仓","parent":"620"},{"id":"652","name":"临河里","parent":"620"}]},{"id":"642","name":"延庆","province":"668","country":"654","sub":[{"id":"643","name":"八达岭","parent":"642"},{"id":"644","name":"张山营","parent":"642"},{"id":"657","name":"延庆城区","parent":"642"}]},{"id":"645","name":"北京周边","province":"668","country":"654","sub":[{"id":"646","name":"燕郊","parent":"645"},{"id":"647","name":"固安","parent":"645"},{"id":"648","name":"廊坊","parent":"645"},{"id":"650","name":"香河","parent":"645"},{"id":"651","name":"涿州","parent":"645"},{"id":"653","name":"怀来","parent":"645"},{"id":"656","name":"大厂","parent":"645"},{"id":"667","name":"唐山","parent":"645"}]}]
         */

        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 20
             * name : 昌平
             * province : 668
             * country : 654
             * sub : [{"id":"54","name":"回龙观","parent":"20"},{"id":"335","name":"天通苑","parent":"20"},{"id":"345","name":"北七家","parent":"20"},{"id":"346","name":"昌平县城","parent":"20"},{"id":"348","name":"霍营","parent":"20"},{"id":"349","name":"立水桥","parent":"20"},{"id":"350","name":"龙泽","parent":"20"},{"id":"351","name":"南口","parent":"20"},{"id":"352","name":"沙河","parent":"20"},{"id":"353","name":"小汤山","parent":"20"},{"id":"354","name":"百善镇","parent":"20"},{"id":"355","name":"阳坊","parent":"20"}]
             */

            private String id;
            private String name;
            private String province;
            private String country;
            private List<SubBean> sub;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getCountry() {
                return country;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public List<SubBean> getSub() {
                return sub;
            }

            public void setSub(List<SubBean> sub) {
                this.sub = sub;
            }

            public static class SubBean {
                /**
                 * id : 54
                 * name : 回龙观
                 * parent : 20
                 */

                private String id;
                private String name;
                private String parent;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getParent() {
                    return parent;
                }

                public void setParent(String parent) {
                    this.parent = parent;
                }
            }
        }
    }
}
