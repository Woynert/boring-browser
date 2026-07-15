(() => {

/*
    NOTES:
    * All comments must be C89 style.
    * Don't use CONST or LET because this WebView for some reason is outdated.
*/

/* Styling. */

var LOGDIV_ID = "___97214589he__woylog";
var eHead = document.getElementsByTagName("head")[0];
var eStyle = document.createElement('style');
eHead.appendChild(eStyle);
eStyle.type = 'text/css';
eStyle.textContent = `
img, video {
  visibility: hidden !important;
}

* {
  background-image: none !important;
  /* color: red !important; */
}

#${LOGDIV_ID} {
  background: black;
  width: fit-content;
  color: lime;
  position: fixed;
  z-index: 2147483647;
  font-size: 12px;
}
`;

/* Logic. */

var frames = 0;
var logdiv = 0;
var TIMEOUT_MS = 3000;

var main = () => {
    /* Insert counter. */
    logdiv = document.createElement("div");
    logdiv.textContent = "0";
    logdiv.id = LOGDIV_ID;
    document.documentElement.insertBefore(logdiv, document.documentElement.firstChild);
};

var loop = () => {

    frames += 1;

    /* Remove iframes.*/

    var r = document.getElementsByTagName('iframe');
    for (var i = r.length-1; i >= 0; i--) { r[i].remove(); }

    var r = document.getElementsByTagName('frame');
    for (var i = r.length-1; i >= 0; i--) { r[i].remove(); }

    /* Update counter.*/

    logdiv.textContent = frames;

};

main();
setInterval(loop, TIMEOUT_MS);

})();
