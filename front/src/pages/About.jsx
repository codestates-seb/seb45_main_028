import React from 'react';


function About() {
  return (
    <main className="bg-white py-4 flex flex-col justify-center items-center mb-32 mt-16">
        <div className="flex items-center mt-10 mb-5">
            <img className="w-full h-full" src='/img/About_img.png' alt="AboutImg1" />
        </div>
        <div>
            <h1 className="text-black text-2xl mb-10 ">SEB45_MAIN_028 / 성환공주와 여섯난쟁이들</h1>
            <img className="w-full h-full" src='/img/About_team.png' alt="AboutImg2" />
        </div>
    </main>
  );
}

export default About;