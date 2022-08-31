import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Main, Landing, Review, ReviewDetail, NotFound } from "src/pages";
import { GetToken } from "./pages/GetToken";
import { Home } from "./pages/Home";
import { Login } from "./pages/Login";
import { Profile } from "./pages/Profile";
import SignUp from "./pages/SignUp/SignUp";
import { Write } from "./pages/Write";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="oauth/redirect" element={<GetToken />} />
        <Route path="main" element={<Main />}>
          <Route index element={<Home />} />
          <Route path="map" element={<div>map</div>} />
          <Route path="profile" element={<Profile />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="login" element={<Login />} />
          <Route path="review" element={<Review />} />
          <Route path="review/:id" element={<ReviewDetail />} />
          <Route path="write" element={<Write />} />
          <Route path="write/:id" element={<div>edit</div>} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
