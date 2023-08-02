document.getElementById('img-edit-btn').addEventListener('click', function () {
    alert('준비중인 기능입니다.');
})

function returnSubmit() {
    let result = true;
    if (document.getElementById('nickName').value.trim() == "") {
        result = false;
        alert("닉네임을 입력해주세요.")
    } else if (document.getElementById('password').value.trim() == "") {
        result = false;
        alert("비밀번호를 입력해주세요.")
    } else if (document.getElementById('check-password').value.trim() == "") {
        result = false;
        alert("비밀번호 확인을 입력해주세요.")
    } else if (document.getElementById('check-password').value.trim() !=
            document.getElementById('passWord').value.trim()) {
        result = false;
        alert("비밀번호 확인이 일치하지 않습니다. 다시 입력해주세요.");
    }

    return result;
}