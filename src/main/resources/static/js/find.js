// 아이디 찾기 버튼
function returnFindId() {
    let result = true;

    if (document.getElementById('find-id-email').value.trim() == "") {
        result = false;
        alert("이메일을 입력해주세요.");
    }

    return result;
}

// 비밀번호 찾기 버튼
function returnFindPwd() {
    let result = true;

    if (document.getElementById('find-pwd-id').value.trim() == "") {
        result = false;
        alert("아이디를 입력해주세요.");
    }

    return result;
}

// 코드 입력
function returnSubmitCode() {
    let result = true;

    if (document.getElementById('input-code').value.trim() == "") {
        result = false;
        alert("코드를 입력해주세요.");
    }

    return result;
}

// 새로운 비밀번호 설정
function returnNewPwd() {
    let result = true;
    let passWord = document.getElementById('new-password').value.trim();
    let checkPwd = document.getElementById('check-new-password').value.trim();

    if (passWord == "") {
        result = false;
        alert("새로운 비밀번호를 입력해주세요.");
    } else if (checkPwd == "") {
        result = false;
        alert("비밀번호 확인을 입력해주세요.");
    } else if (passWord != checkPwd) {
        result = false;
        alert("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
    }

    return result;
}