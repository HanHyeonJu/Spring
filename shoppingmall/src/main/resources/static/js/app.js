$('a.deleteConfirm').click(function () {
  if (!confirm('삭제하시겠습니까?')) return false;
});

$(function () {
  // 페이지컨텐트 - CK에디터 추가
  if ($('#content').length) {
    // 제이쿼리에서 태그선택시 없어도 참이 나오기 때문에 length를 사용(content id가 있어야 length가 나올때만 사용한다는 뜻)
    ClassicEditor.create(document.querySelector('#content')).catch((error) => {
      console.error(error);
    });
  }
  // 상품설명 에디터- CK에디터 추가
  if ($('#description').length) {
    ClassicEditor.create(document.querySelector('#description')).catch((error) => {
      console.error(error);
    });
  }
});
