import {type RouteConfig, index, route} from "@react-router/dev/routes";

export default [
    index("routes/home.tsx"),
    route("/login", "routes/login.tsx"),
    route("/signup", "routes/signup.tsx"),
    route("/profile", "routes/profile.tsx"),
    route("/logout", "routes/logout.tsx"),
    route("/dashboard", "routes/dashboard.tsx"),
    route("/apply", "routes/applicationForm.tsx"),
    route("/roadmaps", "routes/roadMapView.tsx"),
    route("/roadmap/create", "routes/roadMapCreate.tsx"),
    route("/myJobPostings", "routes/myJobPostings.tsx"),
    route("/composeJob", "routes/jobPostingForm.tsx"),
    route("/job/:id/applicants", "routes/jobApplicants.tsx"),
] satisfies RouteConfig;
