import React, { useRef, useState, useEffect } from "react";

const StarRating = ({ rating, reviews }) => (
  <span className="flex gap-2 mt-2">
    <span className="flex items-center gap-1 text-pink-600">
      <svg
        xmlns="http://www.w3.org/2000/svg"
        viewBox="0 0 16 16"
        fill="currentColor"
        className="size-4"
      >
        <path
          fillRule="evenodd"
          d="M8 1.75a.75.75 0 0 1 .692.462l1.41 3.393 3.664.293a.75.75 0 0 1 .428 1.317l-2.791 2.39.853 3.575a.75.75 0 0 1-1.12.814L7.998 12.08l-3.135 1.915a.75.75 0 0 1-1.12-.814l.852-3.574-2.79-2.39a.75.75 0 0 1 .427-1.318l3.663-.293 1.41-3.393A.75.75 0 0 1 8 1.75Z"
          clipRule="evenodd"
        />
      </svg>
      <span className="text-sm font-medium">{rating}</span>
    </span>
    <span className="text-sm text-gray-500">({reviews} reviews)</span>
  </span>
);

const CheckGamesButton = ({ fullWidth = false }) => (
  <button
    type="button"
    className={`rounded-lg bg-pink-500 px-3 py-2 text-sm font-bold text-white ${
      fullWidth ? "w-full" : "w-60"
    }`}
  >
    Check games
  </button>
);

const ImageGallery = ({ images }) => {
  console.log("Is of type " + typeof images);
  console.log(images);

  return (
    <div className="grid grid-cols-4 grid-rows-2 gap-2">
      {images.map((src, i) => (
        <div
          key={i}
          className={`${
            i === 0
              ? "col-span-4 xl:col-span-2 xl:row-span-2 aspect-square"
              : "col-span-2 xl:col-span-1 aspect-square"
          }`}
        >
          <img
            alt=""
            src={src}
            className={"w-full h-full object-cover rounded-lg"}
          />
        </div>
      ))}
    </div>
  );
};

const Description = ({ text }) => {
  const [isClamped, setIsClamped] = useState(false);
  const [expanded, setExpanded] = useState(false);
  const pRef = useRef(null);

  useEffect(() => {
    const el = pRef.current;
    if (!el) return;

    // Temporarily remove clamping to measure natural height
    el.classList.remove("line-clamp-2");
    const fullHeight = el.scrollHeight;
    el.classList.add("line-clamp-2");

    const clampedHeight = el.clientHeight;

    setIsClamped(fullHeight > clampedHeight);
  }, [text]);

  return (
    <div className="max-w-md">
      <p
        ref={pRef}
        className={`mt-4 text-sm text-gray-600 transition-all duration-300 ${
          expanded ? "" : "line-clamp-2"
        }`}
      >
        {text}
      </p>

      {isClamped && (
        <button
          onClick={() => setExpanded(!expanded)}
          className="mt-1 text-sm font-bold text-pink-600 hover:underline"
        >
          {expanded ? "Show less" : "Show more"}
        </button>
      )}
    </div>
  );
};

function TableElement({ table }) {
  return (
    <div className="relative">
      <div className="container mx-auto">
        <div className="grid gap-10 rounded-t-2xl bg-white p-6 pb-10 ring ring-gray-950/5 lg:grid-cols-2 lg:px-20 lg:py-8 mt-4">
          <div className="flex flex-col flex-1">
            <span className="font-medium text-gray-500 hidden sm:inline">
              {table.type}
            </span>
            <span className="mt-2 text-3xl font-semibold text-gray-950 hidden sm:inline">
              {table.name}
            </span>
            <StarRating rating="2.66" reviews="128" />
            <span className="text-sm font-medium text-pink-600">
              {table.address}
            </span>

            <Description text={table.description} />

            <div className="mt-6 hidden lg:block">
              <CheckGamesButton />
            </div>

            <div className="block lg:hidden mt-4">
              <CheckGamesButton fullWidth />
            </div>
          </div>

          {table.images?.length > 0 && <ImageGallery images={table.images} />}
        </div>
      </div>
    </div>
  );
}

export default TableElement;
