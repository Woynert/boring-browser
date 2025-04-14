(function f() {

/* Styling */

let eHead = document.getElementsByTagName("head")[0];
let eStyle = document.createElement('style');
eHead.appendChild(eStyle);
eStyle.type = 'text/css';

let css = `
img, video {
  visibility: hidden !important;
}

* {
  background-image: none !important;
}
`;

eStyle.textContent = css;

/* Remove iframes */

const TIMEOUT_MS = 3000;
const loop = function () {

    let r = document.getElementsByTagName('iframe');
    for (let i = r.length-1; i >= 0; i--) {
        r[i].remove();
    }
    
    setTimeout(loop, TIMEOUT_MS);
};
setTimeout(loop, TIMEOUT_MS);
loop();

})()
