package com.gsy.graduation.data;

import java.util.List;

/**
 *
 */
public class HotMovieData {

    public List<DataBean> data;

    public static class DataBean implements Comparable<DataBean> {


        /**
         * movie : [{"pic_url":"http:","describe":"描述简介","click_value":"357","name":"金刚狼"},{"pic_url":"http:","name":"金刚狼1","click_value":"237","describe":"描述简介"},{"pic_url":"http:","name":"金刚狼2","click_value":"324","describe":"描述简介"}]
         * address : 黑龙江
         * address_city : 哈尔滨市
         */

        public String address;
        public String address_city;
        public List<MovieBean> movie;

        public static class MovieBean {
            /**
             * pic_url : http:
             * describe : 描述简介
             * click_value : 357
             * name : 金刚狼
             */

            public String pic_url;
            public String describe;
            public String click_value;
            public String name;
        }


        @Override
        public int compareTo(DataBean another) {
            int anotherClick = 0;
            int thisClick = 0;
            for (MovieBean movieBean : another.movie) {
                anotherClick = Integer.parseInt(movieBean.click_value) > anotherClick ? Integer.parseInt(movieBean.click_value) : anotherClick;
            }
            for (MovieBean movieBean : this.movie) {
                thisClick = Integer.parseInt(movieBean.click_value) > thisClick ? Integer.parseInt(movieBean.click_value) : thisClick;
            }
            return (anotherClick < thisClick) ? -1 : ((anotherClick == thisClick) ? 0 : 1);
        }
    }


    public static String movie_data = "{\n" +
            "  \"data\" : [\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"324\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"黑龙江\",\n" +
            "      \"address_city\" : \"哈尔滨市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"197\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"327\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"吉林\",\n" +
            "      \"address_city\" : \"长春市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"257\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"117\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"347\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"辽宁\",\n" +
            "      \"address_city\" : \"沈阳市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"387\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"497\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"127\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"河北\",\n" +
            "      \"address_city\" : \"石家庄市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"147\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"189\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"187\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"山西\",\n" +
            "      \"address_city\" : \"太原市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"317\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"100\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"青海\",\n" +
            "      \"address_city\" : \"西宁市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"327\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"127\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"457\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"山东\",\n" +
            "      \"address_city\" : \"济南市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"34\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"19\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"32\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"河南\",\n" +
            "      \"address_city\" : \"郑州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"199\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"287\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"323\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"江苏\",\n" +
            "      \"address_city\" : \"南京市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"134\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"127\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"334\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"安徽\",\n" +
            "      \"address_city\" : \"合肥市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"437\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"347\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"567\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"浙江\",\n" +
            "      \"address_city\" : \"杭州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"347\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"437\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"福建\",\n" +
            "      \"address_city\" : \"福州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"江西\",\n" +
            "      \"address_city\" : \"南昌市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"湖南\",\n" +
            "      \"address_city\" : \"长沙市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"湖北\",\n" +
            "      \"address_city\" : \"武汉市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"广东\",\n" +
            "      \"address_city\" : \"广州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"海南\",\n" +
            "      \"address_city\" : \"海口市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"甘肃\",\n" +
            "      \"address_city\" : \"兰州市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"陕西\",\n" +
            "      \"address_city\" : \"西安市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"四川\",\n" +
            "      \"address_city\" : \"成都市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"贵州省\",\n" +
            "      \"address_city\" : \"贵阳市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"云南\",\n" +
            "      \"address_city\" : \"昆明市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"广西\",\n" +
            "      \"address_city\" : \"南宁市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"宁夏\",\n" +
            "      \"address_city\" : \"银川市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"西藏\",\n" +
            "      \"address_city\" : \"拉萨市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"维吾尔\",\n" +
            "      \"address_city\" : \"乌鲁木齐市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"内蒙古\",\n" +
            "      \"address_city\" : \"呼和浩特市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"北京\",\n" +
            "      \"address_city\" : \"北京市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"天津\",\n" +
            "      \"address_city\" : \"天津市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"上海\",\n" +
            "      \"address_city\" : \"上海市\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"movie\" : [\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼\",\n" +
            "          \"click_value\" : \"357\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼1\",\n" +
            "          \"click_value\" : \"237\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        },\n" +
            "        {\n" +
            "          \"pic_url\" : \"http://stage.meitudata.com/meiyin/585b33553ac3480797.jpg\",\n" +
            "          \"name\" : \"金刚狼2\",\n" +
            "          \"click_value\" : \"397\",\n" +
            "          \"describe\" : \"描述简介\"\n" +
            "        }\n" +
            "      ],\n" +
            "      \"address\" : \"重庆\",\n" +
            "      \"address_city\" : \"重庆市\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
