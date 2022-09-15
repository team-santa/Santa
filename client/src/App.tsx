import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Main, Landing, ReviewList, ReviewDetail, NotFound } from "src/pages";
import { EditProfile } from "./pages/EditProfile";
import EditWrite from "./pages/EditWrite/EditWrite";
import { GetToken } from "./pages/GetToken";
import { Home } from "./pages/Home";
import { KakaoMap } from "./pages/KakaoMap";
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
          <Route path="map" element={<KakaoMap />} />
          <Route path="profile" element={<Profile />} />
          <Route path="editprofile" element={<EditProfile />} />
          <Route path="signup" element={<SignUp />} />
          <Route path="login" element={<Login />} />
          <Route path="review" element={<ReviewList />} />
          <Route path="review/:id" element={<ReviewDetail />} />
          <Route path="write" element={<Write />} />
          <Route path="write/:id" element={<EditWrite />} />
        </Route>
        <Route path="*" element={<NotFound />} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
