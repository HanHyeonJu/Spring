const stages = document.querySelectorAll('.stage');
let s1 = 0;
let s2 = 0;
let s3 = 0;

// js에서 1 == '1'은 맞지만 1 === '1'은 틀린 것, ==은 그냥 값만 비교하는거고 ===가 타입까지 비교하는 것
stages.forEach((stage) => {
  if (stage.textContent === '시작전') s1++;
  else if (stage.textContent === '진행중') s2++;
  else if (stage.textContent === '완료') s3++;
});

const data = {
  labels: ['시작전', '진행중', '완료'],
  datasets: [
    {
      label: 'My First Dataset',
      data: [s1, s2, s3],
      backgroundColor: ['rgb(255, 99, 132)', 'rgb(54, 162, 235)', 'rgb(255, 205, 86)'],
      hoverOffset: 4,
    },
  ],
};

const config = {
  type: 'pie',
  data: data,
};

const myChart = new Chart(document.getElementById('myChart'), config);
