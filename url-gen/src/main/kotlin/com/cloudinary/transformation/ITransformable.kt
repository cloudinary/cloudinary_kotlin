package com.cloudinary.transformation


@TransformationDsl
interface ITransformable<T> : ITransformableImage<T>, ITransformableVideo<T>
