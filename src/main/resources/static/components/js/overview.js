class JsonTreeViewer extends HTMLElement {
    connectedCallback() {
        this.innerHTML = `
  <textarea spellcheck="false">
  {
  "Reasoning": "I need to outline the essential components of a DevOps portfolio project. This includes setting up the infrastructure, implementing CI/CD pipelines, managing containers, monitoring, and security. I will break this down into logical phases that a developer would execute, similar to calling methods in a program.",
  "helperFunctions": [
    {
      "Reasoning": "The first step is to establish the foundation. This involves selecting a cloud provider, creating a VPC, setting up subnets, and launching the initial compute instances or containers. This is the 'setup' phase.",
      "helperFunctions": [
        {
          "Reasoning": "Selects the appropriate cloud provider (e.g., AWS, Azure, GCP) based on configuration or user input.",
          "helperFunctions": [],
          "id": "1302b7dc-1202-406f-b277-41ebf2782f42",
          "name": "selectCloudProvider"
        },
        {
          "Reasoning": "Creates a new Virtual Private Cloud (VPC) with the specified CIDR block and configuration.",
          "helperFunctions": [],
          "id": "cf44968e-8fa1-468b-916e-d13dc483137f",
          "name": "createVPC"
        },
        {
          "Reasoning": "Provisions subnets within the created VPC, defining availability zones and CIDR ranges.",
          "helperFunctions": [],
          "id": "c7ada185-1d31-46b6-8611-55b8c13fa110",
          "name": "setupSubnets"
        },
        {
          "Reasoning": "Launches the initial compute resources (EC2 instances, VMs, or containers) into the prepared subnets.",
          "helperFunctions": [],
          "id": "bed5803d-7046-4bc1-a80c-0fca46c956ae",
          "name": "launchComputeInstances"
        },
        {
          "Reasoning": "Configures networking components such as internet gateways, NAT gateways, and route tables to ensure connectivity.",
          "helperFunctions": [],
          "id": "e23f1498-07d7-47cb-b37a-156fe9ceb33f",
          "name": "configureNetworking"
        },
        {
          "Reasoning": "Applies security policies (Security Groups, Network ACLs) to control traffic flow between resources.",
          "helperFunctions": [],
          "id": "4224f346-3960-4556-8fb7-081f84253dcb",
          "name": "applySecurityPolicies"
        },
        {
          "Reasoning": "Validates that all infrastructure components are healthy and reachable before proceeding to the next phase.",
          "helperFunctions": [],
          "id": "e6ffd732-3ed1-4a6c-a5a7-7efdaefae316",
          "name": "validateInfrastructure"
        }
      ],
      "id": "921eb07c-1edf-4a61-b697-f6bc28a5f655",
      "name": "setupInfrastructure"
    },
    {
      "Reasoning": "Once the infrastructure is ready, we need to define the application code and the process to build and test it. This includes writing Dockerfiles, setting up a repository (like GitHub), and configuring a CI pipeline to run tests on every commit.",
      "helperFunctions": [
        {
          "Reasoning": "Create a Dockerfile that defines the base image, copies application code, sets the working directory, and defines the command to run the application.",
          "helperFunctions": [],
          "id": "a0391f78-1459-4e43-8692-b75f57b6748b",
          "name": "createDockerfile"
        },
        {
          "Reasoning": "Initialize a version control repository (e.g., GitHub) to store the application code, configuration files, and Dockerfiles.",
          "helperFunctions": [],
          "id": "f4f034b8-e54b-46fc-a292-424178c0c584",
          "name": "setupRepository"
        },
        {
          "Reasoning": "Configure a CI/CD pipeline (e.g., GitHub Actions, Jenkins) to automatically build and test the application on every commit pushed to the repository.",
          "helperFunctions": [],
          "id": "e154d762-09b1-4b19-8e2b-118a73c1599b",
          "name": "configurePipeline"
        }
      ],
      "id": "a711f8c6-3343-4973-a138-d50a0df2c379",
      "name": "configureCI"
    },
    {
      "Reasoning": "After the code is built and tested, it needs to be deployed. This involves setting up a CD pipeline that automates the deployment of the containerized application to the target environment (e.g., Kubernetes cluster or ECS).",
      "helperFunctions": [
        {
          "Reasoning": "Define the container image tag and repository details for the application.",
          "helperFunctions": [],
          "id": "20fcca5d-c068-42d5-add2-73fa4bd3ef02",
          "name": "defineContainerImage"
        },
        {
          "Reasoning": "Create the Kubernetes manifest files (Deployment, Service, Ingress) or ECS task definitions required for the application.",
          "helperFunctions": [],
          "id": "d8e8e9df-c72d-49f3-b34b-c790b2ee5c46",
          "name": "generateDeploymentManifests"
        },
        {
          "Reasoning": "Set up the CI/CD orchestration tool (e.g., Jenkins, GitHub Actions, GitLab CI) to trigger the deployment process.",
          "helperFunctions": [],
          "id": "57d3abf5-39d1-4ab3-af18-f92781b37056",
          "name": "setupCDOrchestrator"
        },
        {
          "Reasoning": "Configure the deployment strategy (e.g., Rolling Update, Blue/Green) and rollback policies within the pipeline.",
          "helperFunctions": [],
          "id": "2780db3b-be60-4229-a602-7f6ea2132036",
          "name": "configureDeploymentStrategy"
        },
        {
          "Reasoning": "Define environment-specific variables (secrets, config maps) for the target deployment environment.",
          "helperFunctions": [],
          "id": "d534b496-1101-4f9b-bb64-59ced1e088f3",
          "name": "configureEnvironmentVariables"
        },
        {
          "Reasoning": "Execute a test deployment to a staging environment to validate the pipeline before production.",
          "helperFunctions": [],
          "id": "458dcb4a-ec05-4764-93f2-802f6072f30e",
          "name": "executeStagingDeployment"
        },
        {
          "Reasoning": "Promote the validated deployment from staging to the production environment.",
          "helperFunctions": [],
          "id": "f2e7f9a7-332b-4707-8826-0398620003ae",
          "name": "promoteToProduction"
        }
      ],
      "id": "9b30bb1a-f7b6-4a12-955c-6d120381cf70",
      "name": "configureCD"
    },
    {
      "Reasoning": "A portfolio project must demonstrate observability. We need to implement logging (e.g., CloudWatch, ELK stack), metrics collection, and potentially distributed tracing to show how the system is monitored.",
      "helperFunctions": [
        {
          "Reasoning": "Logging is the foundation of observability. This function will configure the logging infrastructure, define log formats, and set up aggregation pipelines (e.g., sending to CloudWatch or ELK).",
          "helperFunctions": [],
          "id": "6ee3f61b-c922-486b-99fd-418b1a6cf3fc",
          "name": "setupLogging"
        },
        {
          "Reasoning": "Metrics provide quantitative data about system performance. This function will define key performance indicators (KPIs), configure metric collection agents, and set up dashboards or alerting rules.",
          "helperFunctions": [],
          "id": "e771522e-bdcc-4952-8169-116a8400cfe8",
          "name": "setupMetrics"
        },
        {
          "Reasoning": "Distributed tracing allows us to follow requests across services. This function will configure the tracing agent, define service maps, and set up a backend for storing and querying trace data.",
          "helperFunctions": [],
          "id": "2ca5bfbe-4841-4ec9-8297-a068eff6a953",
          "name": "setupTracing"
        }
      ],
      "id": "479b87c1-6b8a-4114-849b-f01ccc64650b",
      "name": "implementMonitoring"
    },
    {
      "Reasoning": "Security is a critical part of modern DevOps. We should include steps for scanning container images for vulnerabilities, managing secrets securely (e.g., using AWS Secrets Manager or HashiCorp Vault), and implementing network security policies.",
      "helperFunctions": [
        {
          "Reasoning": "Scan container images for vulnerabilities before deployment to ensure no known CVEs are introduced into the production environment.",
          "helperFunctions": [],
          "id": "ed856d41-7951-49fb-aeaa-d708dace86ea",
          "name": "scanContainerImages"
        },
        {
          "Reasoning": "Implement a centralized secret management strategy using tools like AWS Secrets Manager or HashiCorp Vault to avoid hardcoding credentials in code or environment variables.",
          "helperFunctions": [],
          "id": "5b4ef5c4-1bc5-4a32-8576-b9526072bc61",
          "name": "manageSecrets"
        },
        {
          "Reasoning": "Define and enforce network security policies (e.g., using AWS Security Groups, Kubernetes NetworkPolicies, or firewalls) to restrict traffic between services and prevent unauthorized access.",
          "helperFunctions": [],
          "id": "3d5956d7-32f6-4686-a120-8e410f1f3d90",
          "name": "implementNetworkPolicies"
        }
      ],
      "id": "87a3f6f4-1c6f-485b-ae3a-304db6543d8e",
      "name": "implementSecurity"
    },
    {
      "Reasoning": "The final step is to document everything. A portfolio needs a README, architecture diagrams, and a demonstration of the live project so potential employers can see the work.",
      "helperFunctions": [
        {
          "Reasoning": "Generates the main project documentation file (README.md). It should include sections for 'About', 'Features', 'Tech Stack', 'Installation', 'Usage', and 'Contributing'. It pulls metadata from the project's package.json or pom.xml to auto-populate the tech stack.",
          "helperFunctions": [],
          "id": "f24462fd-3d1a-43b7-8bf5-154b7414a490",
          "name": "generateReadme"
        },
        {
          "Reasoning": "Creates visual architecture diagrams based on the project's code structure or a provided description. This could involve analyzing the directory tree to generate a component hierarchy or using a tool to create a sequence diagram of the main user flow.",
          "helperFunctions": [],
          "id": "3ee8a69a-6b87-4dd0-817e-ffe1f8991b14",
          "name": "createArchitectureDiagram"
        },
        {
          "Reasoning": "Deploys the application to a live hosting service. This involves building the project (npm run build or mvn package), uploading the artifacts to a storage bucket or container registry, and configuring the hosting provider to serve the application on a specific URL.",
          "helperFunctions": [],
          "id": "e0dcf83e-a984-4a7d-94af-9160e765b974",
          "name": "deployToLiveEnvironment"
        },
        {
          "Reasoning": "Validates that the live deployment is successful and generates a shareable link or QR code that can be included in the final README or LinkedIn profile.",
          "helperFunctions": [],
          "id": "19b3f7ea-5462-40a4-838c-0dc805a6ef34",
          "name": "verifyDeploymentAndGenerateLink"
        }
      ],
      "id": "eccdbaf2-af61-496a-8093-948f158e4987",
      "name": "documentAndDeploy"
    }
  ],
  "id": "32f015eb-f336-47b3-899e-14440b93f252",
  "name": "DevOpsPortfolioProjectPlan"
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
        this.tree.replaceChildren();

        try {
            const json = JSON.parse(this.textarea.value);
            const rootNode = this.createNode(json, json.name);

            this.tree.appendChild(rootNode);
            this.selectNode(rootNode.querySelector(".node-box"));
            this.showDetails(json, "root");
        } catch (error) {
            this.error.textContent = `Invalid JSON: ${error.message}`;
        }
    }

    createNode(value, label) {
        const node = document.createElement("div");
        node.className = "tree-node";

        const box = document.createElement("div");
        box.className = "node-box";

        const title = this.getNodeTitle(value, label);
        box.textContent = title;

        box.addEventListener("click", (event) => {
            event.stopPropagation();

            this.selectNode(box);
            this.showDetails(value, title);
        });

        node.appendChild(box);

        if (!this.isContainer(value)) {
            return node;
        }

        const children = document.createElement("div");
        children.className = "node-children";

        if (Array.isArray(value)) {
    value.forEach((child, index) => {
        const childLabel =
            child &&
            typeof child === "object" &&
            child.name !== undefined &&
            child.name !== null
                ? String(child.name)
                : `[${index}]`;

        children.appendChild(
            // this.createNode(child, "childLabel")
            this.createNode(child, "o")
        );
    });
        } else {
            Object.entries(value).forEach(([key, child]) => {
                if (this.isContainer(child)) {
                    children.appendChild(
                        this.createNode(child, "I")
                    );
                }
            });
        }

        if (children.hasChildNodes()) {
            node.appendChild(children);
        }

        return node;
    }

    getNodeTitle(value, fallback) {
        if (
            value &&
            typeof value === "object" &&
            !Array.isArray(value) &&
            value.title !== undefined &&
            value.title !== null
        ) {
            return String(value.title);
        }

        return String(fallback);
    }

    isContainer(value) {
        return value !== null && typeof value === "object";
    }

    selectNode(box) {
        this.tree
            .querySelectorAll(".node-box.selected")
            .forEach((node) => node.classList.remove("selected"));

        box?.classList.add("selected");
    }

    showDetails(value, title) {
        const heading = this.querySelector(".details-panel h3");
        const output = this.querySelector(".details-panel pre");

        const childrenNames = value.helperFunctions.map((child) => "\n" + child.name);

        heading.textContent = value.name;
        output.textContent = "Reasoning: " + "\n" + value.Reasoning + "\n\n" + "Helper Functions: " + childrenNames;
    }
}

customElements.define("json-tree-viewer", JsonTreeViewer);
