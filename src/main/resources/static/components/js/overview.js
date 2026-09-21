class JsonTreeViewer extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
  <textarea spellcheck="false">{
      "id": 1,
      "title": "First item",
  },
  "items": [
    {
      "id": 2,
      "title": "First item",
      "items": [
        { "id": 4, "title": "Nested item one" },
        { "id": 5, "title": "Nested item two" }
      ]
    },
    {
      "id": 3,
      "title": "Second item"
    }
  ]
}</textarea>

  <button type="button">Render tree</button>

  <div class="error"></div>

  <div class="tree-layout">
    <div class="tree"></div>

    <aside class="details-panel">
      <h3>Object details</h3>
      <pre>Click a box to view its details.</pre>
    </aside>
  </div>
`;
        


        this.textarea = this.querySelector("textarea");
        this.button = this.querySelector("button");
        this.error = this.querySelector(".error");
        this.tree = this.querySelector(".tree");

        this.button.addEventListener("click", () => this.render());

        this.render();
    }
        
    render() {
        this.error.textContent = "";

        try {
            const json = JSON.parse(this.textarea.value);

            this.tree.innerHTML = "";

            const rootNode = this.createNode(json, "root");
            this.tree.appendChild(rootNode);

            // Automatically show the root object's details
            this.showDetails(json, "root");
        } catch (error) {
            this.tree.innerHTML = "";
            this.error.textContent = `Invalid JSON: ${error.message}`;
        }
    }

    createNode(value, label) {
        const node = document.createElement("div");
        node.className = "tree-node";

        const box = document.createElement("div");
        box.className = "node-box";

        // Use the object's title when available.
        // Otherwise use the property name or array index.
        const title =
            value &&
                typeof value === "object" &&
                !Array.isArray(value) &&
                value.title
                ? value.title
                : label;

        box.textContent = title;

        box.addEventListener("click", (event) => {
            event.stopPropagation();

            document
                .querySelectorAll(".node-box")
                .forEach((item) => item.classList.remove("selected"));

            box.classList.add("selected");
            this.showDetails(value, title);
        });

        node.appendChild(box);

        // Only objects and arrays can contain child boxes
        if (value && typeof value === "object") {
            const children = document.createElement("div");
            children.className = "node-children";

            if (Array.isArray(value)) {
                value.forEach((child, index) => {
                    children.appendChild(
                        this.createNode(child, `[${index}]`)
                    );
                });
            } else {
                Object.entries(value).forEach(([key, child]) => {
                    // Only create boxes for nested objects or arrays
                    if (child && typeof child === "object") {
                        children.appendChild(
                            this.createNode(child, key)
                        );
                    }
                });
            }

            if (children.children.length > 0) {
                node.appendChild(children);
            }
        }

        return node;
    }

    showDetails(value, title) {
        const heading = this.querySelector(".details-panel h3");
        const output = this.querySelector(".details-panel pre");

        heading.textContent = title;
        output.textContent = JSON.stringify(value, null, 2);
    }
}

    customElements.define("json-tree-viewer", JsonTreeViewer);