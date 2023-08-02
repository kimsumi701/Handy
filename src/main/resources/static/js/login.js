let id = document.getElementById('userId');
let password = document.getElementById('password');
let checkPwd = document.getElementById('checkPwd');
let nickName = document.getElementById('nickName');
let email = document.getElementById('email');
let phoneNumber = document.getElementById('phoneNumber');

let joinBtn = document.getElementById('join-button');
joinBtn.addEventListener('click', function() {
    if (id.value.length == 0) {
        alert("아이디를 입력해주세요");
    } else if (password.value.length == 0) {
        alert("비밀번호를 입력해주세요");
    } else if (checkPwd.value.length == 0) {
        alert("비밀번호 확인을 입력해주세요");
    } else if (nickName.value.length == 0) {
        alert("닉네임을 입력해주세요");
    } else if (email.value.length == 0) {
        alert("이메일을 입력해주세요");
    } else if (phoneNumber.value.length == 0) {
        alert("휴대전화 번호를 입력해주세요");
    }
})
