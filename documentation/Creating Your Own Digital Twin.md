Previous: [Step 5](Step-5.md)

#**Build the Digital Twin Starter Kit: Creating Your Digital Twin**

##**What you'll learn to do**

Now that you’ve completed the Digital Twin Starter Kit Tutorial, it is time for you to apply what you’ve learned to create your own Digital Twins. The following are some guiding questions and recommendations on how to get started.

##**What you need to do**

**Gather Your Information**

In preparation for creating a Digital Twin, you should assess and answer the following questions.

**Input Data**

-   Do you have data available for your models and analytics?

-   Can the data be accessed from Predix?

-   What is the access frequency of the data?

-   Do you have services that allow access to the data?

-   What contractual/legal obligations are associated with the data?

-   Are there any restrictions on the data, such as export control or government regulatory requirements?

-   Does your data need processing or filtering, such as quality correction (e.g. imputation)?

**The model(s)**

-   Do you have existing models and analytics?

-   What language do you use or would like to use?

-   What type of compute is needed?

-   What are your security and access control needs?

-   What is the input and output data for each model?

-   Do your models and analytics need state management?

**Output data**

-   What is the schema of your output?

-   What are the storage dependencies?

-   Is the output time series data?

**Orchestration**

-   What is the sequence of execution of the data services and the models?

-   Can the models work in parallel?

-   Can the workflow be decomposed into multiple workflows?

**Visualize results**

-   Are the visuals related to the use-case value stories?

-   Are there any specialized charting needs?

-   What is the User Experience you are trying to provide?

**Time to Create Your Digital Twin**

After you have assessed and answered your questions above and you have done your initial design, it’s time to implement your own Digital Twin. The Digital Twin Starter Kit tutorial can be a guide to your own implementation.

We suggest you make sure you have the data to support the fundamental problem you are trying to solve. From our Starter Kit, we know what we have the temperature sensor data related to the rotor model. For your Digital Twin model, you need be sure it can get the data necessary. If not, steps should be taken to collect that data and have it available through a service interface with the appropriate authorization and access control.

Developing your Digital Twin will be an iterative process. As you expand the capability of your models and analytics, data requirements and availability may change. When new types of data become available, your models and analytics’ functionality may expand. Like all software development, you need to be agile through the Digital Twin development lifecycle.

Your Digital Twin analytical output needs to be available and accessible for visualization and as input to other business processes. The primary purpose of Digital Twins is to help make business decisions. The output of Digital Twins will be the input to one or more business applications and analytics. Therefore, the running of Digital Twins should be decoupled from the business processes that will use their output.

In most cases, Digital Twins will be triggered to run either by the availability of new asset data, alerts in a system, and/or by schedule to run at a specific time interval. In essence, Digital Twins will be running on their own, and only in minimal cases run by a person. You will have to decide and design how you want your Digital Twin to be run. Digital Twins will produce results in an automated and somewhat continuous fashion.

In conclusion, Digital Twins are created for business outcomes. The basic steps of creating Digital Twins as laid out in this tutorial are a guide on “why” and “what” you need to consider. It is up to you to decide “how” you apply these steps in your business needs.
