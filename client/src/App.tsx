import { BrowserRouter, Routes, Route } from "react-router-dom";
import { Main, Landing } from "src/pages";

const App = () => {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Landing />} />
        <Route path="main" element={<Main />}>
          <Route index element={<div>home</div>} />
          <Route path="map" element={<div>map</div>} />
          <Route path="profile" element={<div>profile</div>} />
          <Route path="login" element={<div>login</div>} />
          <Route path="review" element={<div>review</div>} />
          <Route path="review/:id" element={<div>review/id</div>} />
          <Route path="write" element={<div>write</div>} />
          <Route path="write/:id" element={<div>edit</div>} />
        </Route>
        <Route path="*" element={<div>Not Found</div>} />
      </Routes>
    </BrowserRouter>
  );
};

export default App;
