<template>
    <div class="class-table">
        <div class="table-wrapper">
            <div class="tabel-container">

                <table>
                    <thead>
                        <tr>
                            <th style="border-radius:20px 0 0 0">时间</th>
                            <!-- <th v-for="(weekNum, weekIndex) in classTableData.courses.length" :key="weekIndex"> {{'周' + digital2Chinese(weekIndex, 'week')}}</th> -->
                            <th>周日</th>
                            <th>周一</th>
                            <th>周二</th>
                            <th>周三</th>
                            <th>周四</th>
                            <th>周五</th>
                            <th style="border-radius:0 20px 0 0">周六</th>

                        </tr>
                    </thead>
                    <tbody>
                        <tr v-for="(lesson, lessonIndex) in classTableData.lessons" :key="lessonIndex">
                            <td>
                                <!-- <p>{{'第' + digital2Chinese(lessonIndex+1) + "节"}}</p> -->
                                <p class="period">{{ lesson }}</p>
                            </td>
                            <td v-for="(course, courseIndex) in classTableData.courses"
                            :class="classTableData.courses[courseIndex][lessonIndex]?'active':''" :key="courseIndex">
                                {{classTableData.courses[courseIndex][lessonIndex] || ' '}}
                            </td>
                        </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</template>

<script>

export default {
    data() {
        return {
            classTableData: {
                lessons: [
                    '08:15-09:00',
                    '09:10-09:55',
                    '10:15-11:00',
                    '11:10-11:55',
                    '13:00-13:45',
                    '13:55-14:40',
                    '15:00-15:45',
                    '15:55-16:40',
                    '16:50-17:35',
                    '18:00-18:40',
                ],
                courses: [
                    ['', '', '', '', '', '', '', ''],
                    ['机电一体化(CS285.01)', '机电一体化(CS285.01)', '现代生命科学导论(BIO1011.01)', '现代生命科学导论(BIO1011.01)', '经典文明导读(GESS1025.01)', '', '游泳(GEPE1026.02)', '游泳(GEPE1026.02)'],
                    ['', '', '信号与系统(EE150L.08)', '信号与系统(EE150L.08)', '电磁学(EE130.01)', '电磁学(EE130.01)', '', ''],
                    ['机电一体化(CS285.01)', '机电一体化(CS285.01)', '现代生命科学导论(BIO1011.01)', '现代生命科学导论(BIO1011.01)', '', '', '', ''],
                    ['', '', '信号与系统(EE150L.08)', '信号与系统(EE150L.08)', '电磁学(EE130.01)', '电磁学(EE130.01)', '', ''],
                    ['', '信号与系统实验(EE150L.08)', '信号与系统实验(EE150L.08)', '', '马克思主义原理(GESS1014.02)', '马克思主义原理(GESS1014.02)', '', ''],
                    ['', '', '', '', '', '', '信号与系统实验(EE150L.08)', '信号与系统实验(EE150L.08)'],
                ]
            }

        }
    },
    created() {
    },
    methods: {
        /**
        * 数字转中文
        * @param {Number} num 需转数字
        * @param {String} identifier 标识符
        * @returns {String} 转换后的中文
        */
        digital2Chinese(num, identifier) {
            const character = ['零', '一', '二', '三', '四', '五', '六', '七', '八', '九', '十', '十一', '十二']
            return identifier === 'week' && (num === 0 || num === 7) ? '日' : character[num]
        }
    }
}
</script>

<style lang="scss" scoped>
.class-table {
    .table-wrapper {
        width: 100%;
        // height: 100%;
        overflow: auto;
    }
    .active {
        color: white !important;
        background-color: #388FB4;
        font-size:12px !important;
        cursor: pointer;
    }
    .tabel-container {
        margin: 0 10px;

        table {
            table-layout: fixed;
            width: 100%;

            thead {
                background-color: #015478;
                th {
                    color: #fff;
                    // line-height: 10px;
                    font-weight: 400;
                    font-size:16px;
                }
            }
            tbody {
                background-color: #eaf2ff;
                td {
                    color: #677998;
                    // line-height: 3px;
                }
            }
            th,
            td {
                width: 60px;
                padding: 10px 2px;
                font-size: 14px;
                text-align: center;
            }

            tr td:first-child {
                color: #333;
                .period {
                    font-size: 13px;
                    font-weight: 500;
                }
            }
        }
    }
}
</style>
