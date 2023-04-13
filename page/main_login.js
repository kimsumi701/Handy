let menu = document.getElementsByClassName('menu');
let menuBar = document.getElementById('menu-bar');
let menuCloseBtn = document.getElementById('close-button');

// 메뉴버튼 클릭
menuBar.addEventListener('click', function () {    
    // menu[0].style.display = 'block';
    menu[0].style.top = '0';
    menuBar.style.display = 'none';
});

// 메뉴 닫기 버튼 클릭
menuCloseBtn.addEventListener('click', function () {
    menu[0].style.top = '-100%';
    menuBar.style.display = 'block';
});

let editBtn = document.querySelectorAll('#edit-button');
let editBox2 = document.getElementsByClassName('edit-box-02');
let saveBox3 = document.getElementsByClassName('edit-box-03');

let favoriteBtn = document.querySelectorAll('#Favorite-button');
// 즐겨찾기 버튼 클릭
const favoriteBtnClick = (event) => {    
    let hasClass = event.target.classList.toggle('favorite-btn-toggle');

    if (hasClass) {
        event.target.style.color = '#ffffff';
    } else {
        event.target.style.color = '#fb0e0e';
    }
}

favoriteBtn.forEach(btn => btn.addEventListener('click', favoriteBtnClick));

let pageUp = document.getElementById('page-top-button');

// 페이지 상단가기 버튼 클릭
const pageUpBtnClick = () => {
    window.scrollTo(0, 0);
}

pageUp.addEventListener('click', pageUpBtnClick);

// 편집 버튼
const editBtnClick = (event) => {
    event.target.style.display = 'none';
    const targetNode = event.target.parentNode.parentNode.parentNode;    
    
    // 단축키 수정 가능 상태
    let editDisabled = document.querySelectorAll('.key-list');

    editDisabled.forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == targetNode) {                        
            Array.from(item.children).forEach(children => {
                if (children.classList.contains('key')) {                    
                    children.children[0].disabled = false;
                }                
            })
        }
    });
        
    // 단축키 설명 수정 가능 상태
    let exampleDisabled = document.querySelectorAll('.key-explanation input');

    exampleDisabled.forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == targetNode) {            
            item.disabled = false;
        }
    })

    // 플러스 마이너스 버튼
    Array.from(editBox2).forEach(box => {
        if (box.parentNode.parentNode.parentNode == targetNode) {
            box.style.display = 'block';
        };
    });
    
    // 확인 버튼
    Array.from(saveBox3).forEach(box => {
        if ((box.parentNode.parentNode || box.parentNode.parentNode.parentNode) == targetNode) {
            box.style.display = 'block';
        };
    })       
}


editBtn.forEach(btn => btn.addEventListener('click', editBtnClick));

// 키입력
// document.querySelectorAll('.key input').forEach(key => {
//     key.addEventListener('keyup', (event) => {
//         console.log(event.key);

//         if (key.value === '' && event.key != 'Backspace') {
//             if (event.key === 'Control') {
//                 key.value = 'Ctrl';
//             } else if (event.key === 'Escape') {
//                 key.value = 'Esc';
//             } else if (event.key === '') {
//                 key.value = 'Space';
//             }
//             else {
//                 key.value = event.key;
//             }
            
//        }
//     })
// })

let saveBtn = document.querySelectorAll('#save-button');
let deleteBtn = document.querySelectorAll('#delete-button');

// 확인 버튼 클릭
const savebtnClick = (event) => {    
    event.target.parentNode.style.display = 'none';
    const targetNode = event.target.parentNode.parentNode.parentNode;            

    // 단축키 수정 가능 상태
    let editDisabled = document.querySelectorAll('.key-list');

    editDisabled.forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == targetNode) {
            Array.from(item.children).forEach(children => {
                if (children.classList.contains('key')) {
                    children.children[0].disabled = true;
                }
            })
        }
    });

    // 단축키 설명 수정 가능 상태
    let exampleDisabled = document.querySelectorAll('.key-explanation input');

    exampleDisabled.forEach(item => {
        if (item.parentNode.parentNode.parentNode.parentNode == targetNode) {
            item.disabled = true;
        }
    })

    // 추가된 단축키 input 글씨 색상
    let addKeyInput = document.querySelectorAll('.key-add-input');
            
    addKeyInput.forEach(input => {
        let keyTarget = input.parentNode.parentNode.parentNode.parentNode.parentNode.parentNode;                   
        if (keyTarget == targetNode) {     
            console.log(input)
            
            input.style.color = '#252525';
        }
    })

    // 플러스 마이너스 버튼
    Array.from(editBox2).forEach(box => {
        if (box.parentNode.parentNode.parentNode == targetNode) {
            box.style.display = 'none';
        }
    });
    
    // 삭제 버튼 비활성화
    let deleteBtn = document.querySelectorAll('#delete-button');        

    deleteBtn.forEach(btn => {
        if (btn.parentNode.parentNode.parentNode == targetNode) {            
            btn.style.display = 'none';            
        }
    }); 
    
    // 확인버튼
    Array.from(editBtn).forEach(btn => {
        if (btn.parentNode.parentNode.parentNode == targetNode) {
            btn.style.display = 'block';
        }
    }); 
}

saveBtn.forEach(btn => btn.addEventListener('click', savebtnClick));


let minusBtn = document.querySelectorAll('#minus-shortcut-button');

// 마이너스 버튼 클릭
const minusBtnClick = (event) => {    
    const targetNode = event.target.parentNode.parentNode.parentNode.parentNode;
    let deleteBtn = document.querySelectorAll('#delete-button');        

    deleteBtn.forEach(btn => {
        if (btn.parentNode.parentNode.parentNode == targetNode) {            
            btn.style.display = 'block'; 
        }
    });
}

minusBtn.forEach(btn => btn.addEventListener('click', minusBtnClick));

let plusBtn = document.querySelectorAll('#plus-shortcut-button');
let listBox = document.querySelectorAll('.shortcut-box');

// 삭제 버튼 클릭
function deleteBtnClick(event) {      
    if (event.target.parentNode.nextElementSibling != null) {
        event.target.parentNode.nextElementSibling.remove();       
    }

    event.target.parentNode.remove();       
}

deleteBtn.forEach(btn => btn.addEventListener('click', deleteBtnClick));

// 플러스 버튼 클릭
const plusBtnClick = (event) => {
    let targetNode = event.target.parentNode.parentNode.parentNode.parentNode;  
    
    let line2 = '<div class="line2"></div>';

    let resultHTML =  `    
    <div class="add-shortcut-list shortcut-list">
        <div class="add-key-box key-box">
            <ul class="add-key-list key-list">
                <li class="key">
                    <input class="key-add-input" type="text" placeholder="Key1">
                    </li>
                <li>+</li>
                <li class="key">
                    <input class="key-add-input" type="text" placeholder="Key2">
                </li>
                <li>+</li>
                <li class="key">
                    <input class="key-add-input" type="text" placeholder="Key3">
                </li>
                <li>+</li>
                <li class="key">
                    <input class="key-add-input" type="text" placeholder="Key4">
                </li>
            </ul> 
        </div> 
        <div class="add-key-explanation key-explanation">                                               
            <input type="text" placeholder="단축키 설명을 입력해주세요">
            <button class="fa-solid fa-caret-up fa-rotate-180" id="example-button" style="color: #252525;"></button> 
        </div>
        <button class="fa-solid fa-xmark" id="delete-button" style="color: #252525;" onclick="deleteBtnClick(event)"></button>
    </div>`;    

    listBox.forEach(box => {                
        if (box.parentNode == targetNode) {  
            if (box.children.length != 0) {
                box.insertAdjacentHTML('beforeend', line2);
            }
            box.insertAdjacentHTML('beforeend',resultHTML);                    
        }
    })        
    deleteBtn.forEach(btn => btn.addEventListener('click', deleteBtnClick));
}

plusBtn.forEach(btn => { btn.addEventListener('click', plusBtnClick) });


