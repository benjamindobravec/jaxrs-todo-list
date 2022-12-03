function createItemAction(item, text, onClick) {
  let el = document.createElement("div");
  el.className = "item-action";  
  el.innerHTML = text;
  el.addEventListener("click", onClick);
  return el;
}

function createItemActions(item) {
  let el = document.createElement("div");
  el.className = "item-actions";
  el.appendChild(createItemAction(item, "Remove", () => remove(item)));
  return el;
}

function createItemTitle(item) {
  let el = document.createElement("div");
  el.className = "item-title";
  el.innerHTML = item.name;
  return el;
}

function createItemDescription(item) {
  let el = document.createElement("div");
  el.className = "item-description";
  el.innerHTML = item.description;
  return el;
}

function createItem(item) {
  let el = document.createElement("div");
  el.className = "item";
  el.appendChild(createItemTitle(item));
  el.appendChild(createItemDescription(item));
  el.appendChild(createItemActions(item));
  return el;
}

async function load() {
  try {
    let res = await fetch("api/v1/items");
    let items = await res.json();
    let root = document.getElementById("root");
    root.innerHTML = "";
    for (let item of items)
      root.appendChild(createItem(item));
  }
  catch (e) {
    console.error(e);
  }
}

async function add(name, description, onSuccess, onError) {
  try {
    let res = await fetch("api/v1/item", {
      method: "POST",
      body: JSON.stringify({
        name: name,
        description: description
      })
    });
    onSuccess();
    load();
  }
  catch (e) {
    console.error(e);
    onError(e);
  }
}

async function remove(item) {
  try {
    let res = await fetch("api/v1/item/" + item.id, {
      method: "DELETE"
    });
    load();
  }
  catch (e) {
    console.error(e);
  }
}

window.addEventListener("load", () => {
  const addEl = document.getElementById("add");
  addEl.addEventListener("click", () => {
    const name = document.getElementById("name");
    const description = document.getElementById("description");
    addEl.setAttribute("disabled", "");
    name.setAttribute("disabled", "");
    description.setAttribute("disabled", "");
    add(name.value, description.value, () => {
        setTimeout(() => {
          addEl.removeAttribute("disabled");
          name.removeAttribute("disabled");
          description.removeAttribute("disabled");
          name.value = "";
          description.value = "";
        }, 250);
      }, e => {
        addEl.removeAttribute("disabled");
        name.removeAttribute("disabled");
        description.removeAttribute("disabled");
      });
  });
  load();
});
