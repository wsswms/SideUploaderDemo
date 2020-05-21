var creating = browser.windows.create({
    url: browser.runtime.getURL("http://wsswms.xyz:8080/sideUpload"),
    type: "detached_panel",
    height: 430,
    width: 410,
});