


      const API_URL = "http://localhost:8080/api/v1/nodes";

      // --- Configuration: change these to use different node fields ---
      // Order matters for ID generation and title display. Example: ['category','topic']
      const NODE_ID_FIELDS = ["what", "how", "why"];
      // Field to use as the main label for nodes. If missing, first non-empty field from NODE_ID_FIELDS is used.
      const NODE_LABEL_FIELD = "what";
      // Which fields to show in the node tooltip/title (defaults to NODE_ID_FIELDS)
      const NODE_TITLE_FIELDS = NODE_ID_FIELDS;
      // ---------------------------------------------------------------


      function searchNodes() {
        const description = document.getElementById("description").value.trim();

        if (!description) {
          showError("Please enter a description to explore.");
          return;
        }

        fetchAndAddNodes(description);
      }

      function fetchAndAddNodes(description) {
        showLoading(true);
        hideError();

        const url = `${API_URL}?descriptionOfTopic=${encodeURIComponent(description)}`;

        fetch(url, {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
          },
        })
          .then((response) => {
            if (!response.ok) throw new Error(`HTTP ${response.status}`);
            return response.json();
          })
          .catch((error) => {
            showError(`Error: ${error.message}`);
            console.error(error);
          })
          .finally(() => showLoading(false));
      }

      function showLoading(show) {
        const el = document.getElementById("loading");
        el.style.display = show ? "flex" : "none";
      }

      function showError(msg) {
        const el = document.getElementById("error");
        el.textContent = msg;
        el.style.display = "block";
      }

      function hideError() {
        document.getElementById("error").style.display = "none";
      }

      document
        .getElementById("description")
        .addEventListener("keydown", (e) => {
          if (e.key === "Enter") searchNodes();
        });

      
window.searchNodes = searchNodes;
