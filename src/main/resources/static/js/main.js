calendarInit();

/*
    달력 렌더링 할 때 필요한 정보 목록
    현재 월(초기값 : 현재 시간)
    금월 마지막일 날짜와 요일
    전월 마지막일 날짜와 요일
*/

function calendarInit() {
    // 날짜 정보 가져오기
    const date = new Date(); // 현재 날짜(로컬 기준)
    const utc = date.getTime() + (date.getTimezoneOffset() * 60 * 1000); // utc 표준시 도출
    const kstGap = 9 * 60 * 60 * 1000; // 한국 kst 기준시간 더하기
    const today = new Date(utc + kstGap); // 한국 시간으로 date 객체 만들기(오늘)

    let thisMonth = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    // 달력에서 표기하는 날짜 객체

    let currentYear = thisMonth.getFullYear();
    let currentMonth = thisMonth.getMonth();
    let currentDate = thisMonth.getDate();

    // 캘린더 렌더링
    renderCalender(thisMonth);

    function renderCalender(thisMonth) {

        currentYear = thisMonth.getFullYear();
        currentMonth = thisMonth.getMonth();
        currentDate = thisMonth.getDate();

        // 이전 달의 마지막 날 날짜와 요일 구하기
        const startDay = new Date(currentYear, currentMonth, 0);
        const prevDate = startDay.getDate();
        const prevDay = startDay.getDay();

        // 이번 달의 마지막날 날짜와 요일 구하기
        const endDay = new Date(currentYear, currentMonth + 1, 0);
        const nextDate = endDay.getDate();
        const nextDay = endDay.getDay();

        // 현재 년도, 월 출력
        document.getElementsByClassName('year-month')[0].innerText = currentYear + '.' + (currentMonth + 1);

        const calendar = document.querySelector('.dates');
        calendar.innerHTML = '';

        // 지난달
        for (let i = prevDate - prevDay; i <= prevDate; i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day prev disable" data-date=' + i + '>' + i + '</div>'
        }
        // 이번달
        for (let i = 1; i <= nextDate; i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day current" ' +
                'data-date=' + currentYear + '.' + (currentMonth + 1) + '.' + i + '>' + i + progressBar(i) + '</div>'
        }

        // 다음달
        for (let i = 1; i <= (7 - nextDay == 7 ? 0 : 7 - nextDay) + 6; i++) {
            calendar.innerHTML = calendar.innerHTML + '<div class="day next disable" data-date=' + i + '>' + i + '</div>'
        }

        // 오늘 날짜 표시
        if (today.getMonth() == currentMonth) {
            let selectDate = today.getDate();
            let currentMonthDate = document.querySelectorAll('.dates .current');
            currentMonthDate[selectDate - 1].classList.add('select_day');
        }

    }

    // move prev month
    document.getElementsByClassName('move-prev')[0].addEventListener('click', function () {
        thisMonth = new Date(currentYear, currentMonth - 1, 1);
        renderCalender(thisMonth);
    });

    // move next month
    document.getElementsByClassName('move-next')[0].addEventListener('click', function () {
        thisMonth = new Date(currentYear, currentMonth + 1, 1);
        renderCalender(thisMonth);
    });

    // click date
    document.querySelectorAll('.dates').forEach((i) => {
        i.addEventListener('click', function (event) {
            let selectDay = event.target.getAttribute('data-date');
            // alert("click test\n" + selectDay);
            let selectDate = selectDay.split('.').reverse()[0]; // 선택한 날짜
            let currentMonthDate = document.querySelectorAll('.dates .current');
            currentMonthDate.forEach((i) => {
                i.classList.remove('select_day'); // 이전 선택 날짜 삭제
            });
            currentMonthDate[selectDate - 1].classList.add('select_day'); // 현재 선택 날짜
        });
    });


}

function progressBar(i) {
    i = Math.floor(Math.random() * 50 + 3); // random data
    let width, color_code;
    let textColor = "color:";
    let backgroundColor = "background: ";
    let r = 2, w = 0; // r = 임의 스플릿 반복 횟수, w = 넘치는 요소 제한
    let value = i * r;

    if (value <= 25) color_code = "#FF7E7E";
    else if (value > 25 && value <= 80) color_code = "#2EE47A";
//    else if (value > 50 && value < 80) color_code = "deepskyblue";
    else if (value > 80 && value <= 100) color_code = "#5F7FFB";
    else color_code = "#00219B";

    textColor += color_code;
    backgroundColor += color_code;

    let str = '<span class="percent-text" style=' + textColor + '>' + i * r + '%</span>' +
        '<div class="progress-container">';
    for (let j = 0; j < r; j++) {
        w += i;
        if (w > 100) // progress + split 요소 너비의 합이 100% 이상이 되면
            i -= w - 100;  // 넘치는 부분 자르기
        width = "width:" + i + "%;";
        str += '<div class="progress-bar" style="'+  width + backgroundColor +'">&nbsp;</div>'
        if (j < r - 1) {
            str += '<div class="split-bar">&nbsp;</div>';
            w += 5; // split-bar 비율
        }
    }
    str += '</div>';
    return str;
}

/* daily time graph sample */

// var scriptElement = document.createElement("script");
// scriptElement.src = "https://www.gstatic.com/charts/loader.js";
// scriptElement.defer = true;
//
// scriptElement.onload = function () {
//     google.charts.load("current", {packages: ["timeline"]});
//     google.charts.setOnLoadCallback(drawChart);
// };
// document.head.appendChild(scriptElement);
//
// google.charts.load("current", {packages: ["timeline"]});
// google.charts.setOnLoadCallback(drawChart);
//
// function drawChart() {
//
//     var container = document.getElementById('example5.2');
//     var chart = new google.visualization.Timeline(container);
//     var dataTable = new google.visualization.DataTable();
//
//     dataTable.addColumn({type: 'string', id: 'date'});
//     dataTable.addColumn({type: 'date', id: 'Start'});
//     dataTable.addColumn({type: 'date', id: 'End'});
//     dataTable.addRows([
//         ['1', new Date(0, 0, 0, 12, 0, 0), new Date(0, 0, 0, 14, 0, 0),],
//         ['1', new Date(0, 0, 0, 15, 0, 0), new Date(0, 0, 0, 17, 0, 0),],
//         ['2', new Date(0, 0, 0, 14, 30, 0), new Date(0, 0, 0, 16, 0, 0)],
//         ['3', new Date(0, 0, 0, 16, 30, 0), new Date(0, 0, 0, 19, 0, 0)],
//         ['4', new Date(0, 0, 0, 12, 30, 0), new Date(0, 0, 0, 14, 0, 0)],
//         ['5', new Date(0, 0, 0, 14, 30, 0), new Date(0, 0, 0, 16, 0, 0)],
//         ['6', new Date(0, 0, 0, 16, 30, 0), new Date(0, 0, 0, 18, 0, 0)],
//         ['7', new Date(0, 0, 0, 12, 30, 0), new Date(0, 0, 0, 14, 0, 0)],
//         ['8', new Date(0, 0, 0, 14, 30, 0), new Date(0, 0, 0, 16, 0, 0)],
//         ['9', new Date(0, 0, 0, 16, 30, 0), new Date(0, 0, 0, 18, 30, 0)]]);
//
//     var options = {
//         timeline: {singleColor: '#7B68EE'},
//         height: 397,
//         hAxis: {
//             ticks: [new Date(0, 0, 0, 1),new Date(0, 0, 0, 2),new Date(0, 0, 0, 3),new Date(0, 0, 0, 4),new Date(0, 0, 0, 5),new Date(0, 0, 0, 6),new Date(0, 0, 0, 7),new Date(0, 0, 0, 8)],
//             // minValue: new Date(0, 0, 0, 1),
//             // maxValue: new Date(0, 0, 0, 24),
//         },
//         axes: {
//             x: {
//                 0: {side: 'top'}
//             }
//         },
//     };
//     chart.draw(dataTable, options);
// }
// //create trigger to resizeEnd event
// $(window).resize(function() {
//     if(this.resizeTO) clearTimeout(this.resizeTO);
//     this.resizeTO = setTimeout(function() {
//         $(this).trigger('resizeEnd');
//     }, 500);
// });
//
// //redraw graph when window resize is completed
// $(window).on('resizeEnd', function() {
//     drawChart();
// });